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

        // user input
        Scanner userInput = new Scanner(System.in);

        while (true) {

            // send messages to server
            char userChar = searchedChar(userInput);
            String userString = searchedString(userInput);
            sendUserInput(userChar, userString);

            // server reply
            try {
                String serverReply = bufferedReader.readLine();
                System.out.println("Server: " + serverReply); // print server reply
            } catch (SocketException e) { // in case the server is no longer running after client already connected
                System.err.println(e);
                System.err.println("Lost Connection to Server");
                System.exit(0);
            }

            System.out.println("Would you like to repeat? (y/n)");
            char userChoice = userInput.nextLine().toLowerCase().charAt(0);

            while (true) {
                if (userChoice == 'y' || userChoice == 'n')
                    break;
                System.out.println("Incorrect input!\n  Would you like to repeat? (Y/N)");
                userChoice = userInput.nextLine().toLowerCase().charAt(0);
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
        System.out.println("Thank You!");
    }

    // getting the searched char from the user
    public static char searchedChar(Scanner userInput) {
        String ch = "";
        // ensuring that the user will input a valid string
        do {
            // get user input
            // if user enters a string, only first character will be used
            System.out.print("Enter a Character to be searched: ");

            ch = userInput.nextLine().toLowerCase();
            if (ch == null || ch.isEmpty() || ch == "")
                System.out.println("Please enter a value!");

            if (ch.length() > 1)
                System.out.println("You have entered multiple characters. Only the first one will be considered");

        } while (ch.length() == 0);

        return ch.charAt(0);
    }

    // getting the searched String from the user
    public static String searchedString(Scanner userInput) {

        String string = "";
        // ensuring that the user will input a valid string

        do {
            // get user input
            System.out.print("Enter string: ");

            string = userInput.nextLine().toLowerCase();
            if (string.length() <= 0)
                System.out.print("Please enter a value!");
        } while (string.length() <= 0);

        return string;
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

    private static void sendUserInput(char character, String string) {
        try {
            bufferedWriter.write(string.toString()); // send string first
            bufferedWriter.newLine(); // send new line character
            bufferedWriter.write(character); // then send character
            bufferedWriter.newLine(); // send new line character
            bufferedWriter.flush(); // flush stream
        } catch (SocketException e) {
            System.err.println(e);
            System.err.println("Connection to Server Lost");
            System.exit(0);
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
