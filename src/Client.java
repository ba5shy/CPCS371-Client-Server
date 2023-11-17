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

     // getting the searched char from the user
    public static String searchedchar(Scanner userInput){
        String ch = "";
       // ensuring that the user will input a valid string 
        do{
        // get user input
        // if user enters a string, only first character will be used
        System.out.print("Enter a Character to be searched: "); 
   
        ch = userInput.nextLine().toLowerCase();
        if (ch == null || ch.isEmpty() || ch == "")
            System.out.println("please enter some value! ");
     
        if(ch.length() > 1)
            System.out.println("you have entered multiable characters! Only the first one will be considered");
            
        ch = ch;
        } while(ch.length() == 0);
        
        return ch;
    }
    
       // getting the searched String from the user
    public static StringBuffer searchedString(Scanner userInput){
        
        StringBuffer string = new StringBuffer("");
        // ensuring that the user will input a valid string 
      
        do{
        // get user input
        System.out.println("Enter string: ");
        
        string = new StringBuffer(userInput.nextLine().toLowerCase());            
        if(string.length() <= 0)
                System.out.print("You haven't entered anything!! ");   
        }while (string.length() <= 0);
        
        return string;
    }
    public static void main(String[] args) throws IOException {

        // create socket
        createSocket();
        
        // user input
         Scanner userInput = new Scanner(System.in);

        while (true) {

            // send messages to server
            sendUserInput(searchedchar(userInput).charAt(0),searchedString(userInput));

            // server reply

            String serverReply = bufferedReader.readLine();
            System.out.println("Server: " + serverReply); // print server reply

            // server reply

            String serverReply = bufferedReader.readLine();
            System.out.println("Server: " + serverReply); // print server reply

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

    private static void sendUserInput(char character , StringBuffer string) throws IOException {
        try {
            bufferedWriter.write(string.toString()); // send string first
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
