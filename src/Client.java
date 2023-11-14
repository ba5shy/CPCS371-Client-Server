import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        Socket clientSocket = null;
        InputStreamReader input = null; // receive data from server
        OutputStreamWriter output = null; // send data to server

        // use buffer to speed up operation

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        // create socket
        try{
            clientSocket = new Socket("localhost", 9000); // used to connect to the server
            input = new InputStreamReader(clientSocket.getInputStream());
            output = new OutputStreamWriter(clientSocket.getOutputStream());

            bufferedReader = new BufferedReader(input);
            bufferedWriter = new BufferedWriter(output);

            Scanner userInput = new Scanner(System.in); // user input
            char character;
            String string;
            char userChoice;

            while(true){

                // get user input

                System.out.println("Enter a Character to be searched: ");
                character = userInput.nextLine().charAt(0);
                System.out.println("Enter string: ");
                string = userInput.nextLine();

                // send messages to server

                bufferedWriter.write(string);  
                bufferedWriter.newLine(); // send new line character
                bufferedWriter.write(character);  
                bufferedWriter.newLine(); // send new line character
                bufferedWriter.flush(); // flush stream 

                // server reply

                String serverReply = bufferedReader.readLine(); 
                System.out.println(serverReply); // print server reply

                System.out.println("Would you like to repeat?");
                userChoice = userInput.nextLine().charAt(0);

                if(userChoice == 'n')
                    break;
            }
            userInput.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(clientSocket != null)
                    clientSocket.close();
                if(input != null)
                    input.close();
                if(output != null)
                    output.close();
                if(bufferedReader != null)
                    bufferedReader.close();
                if(bufferedWriter != null)
                    bufferedWriter.close();
                
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
