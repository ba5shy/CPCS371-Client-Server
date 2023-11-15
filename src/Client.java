import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    static Socket socket; // socket class uses TCP/IP
    static InputStreamReader input; // receive data from server
    static OutputStreamWriter output; // send data to server

    // use buffer to speed up operation

    static BufferedReader bufferedReader;
    static BufferedWriter bufferedWriter;

    public static void main(String[] args) throws IOException {

        // create socket
        createSocket();
        Scanner userInput = new Scanner(System.in); // user input

        while (true) {

            // get user input
            // if user enters a string, only first character will be used
            System.out.println("Enter a Character to be searched: "); 
            char character = userInput.nextLine().charAt(0);
            System.out.println("Enter string: ");
            String string = userInput.nextLine();

            // send messages to server
            sendUserInput(string, character);

            // server reply

            String serverReply = bufferedReader.readLine();
            System.out.println("Server: " + serverReply); // print server reply

            System.out.println("Would you like to repeat? (y/n)");
            char userChoice = userInput.nextLine().charAt(0);

            while (true) {
                if (userChoice == 'y' || userChoice == 'n')
                    break;
                System.out.println("Incorrect input (y/n)");
                userChoice = userInput.nextLine().charAt(0);
            }

            // send user choice to server
            bufferedWriter.write(userChoice);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // if user choice is no, break
            if (userChoice == 'n')
                break;

        }
        userInput.close(); // close Scanner

    }

    private static void createSocket() {
        try {
            socket = new Socket("localhost", 9000); // used to connect to the server
            input = new InputStreamReader(socket.getInputStream());
            output = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(input);
            bufferedWriter = new BufferedWriter(output);
        } catch (UnknownHostException e) {
            System.err.println(e);
            System.err.println("Port number provided is Incorrect");
        } catch (ConnectException e) {
            System.err.println(e);
            System.err.println("Server is not running");
            System.exit(0);
        } catch (IOException e) {
            System.err.println(e);
        }
        
    }

    private static void sendUserInput(String string, char character) throws IOException {
        try {
            bufferedWriter.write(string); // send string first
            bufferedWriter.newLine(); // send new line character
            bufferedWriter.write(character); // then send character
            bufferedWriter.newLine(); // send new line character
            bufferedWriter.flush(); // flush stream
        } catch (SocketException e) {
            System.err.println("Connection to Server Lost");
            System.exit(0);
        }

    }
}
