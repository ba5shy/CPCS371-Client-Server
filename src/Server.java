import java.net.*;
import java.io.*;


public class Server {
    public static void main(String[] args) throws IOException{
        // a little bit similar to the client

        Socket socket = null;
        InputStreamReader input = null; // receive data from server
        OutputStreamWriter output = null; // send data to server
        ServerSocket serverSocket = new ServerSocket(9000); // waits for requests on a certain port number (9000)


        // use buffer to speed up operation

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        while(true){
            try{
                socket = serverSocket.accept();
                input = new InputStreamReader(socket.getInputStream());
                output = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(input);
                bufferedWriter = new BufferedWriter(output);


                // second while loop to keep serving client 
                while(true){
                    String string = bufferedReader.readLine();
                    char character = bufferedReader.readLine().charAt(0);
                    
                    System.out.println("Client: " + string + character);

                    bufferedWriter.write("Message received");
                    bufferedWriter.newLine();
                    bufferedWriter.write(character + " " + string);
                    bufferedWriter.flush();

                    break;
                }

                socket.close();
                input.close();
                output.close();


            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


    }
}
