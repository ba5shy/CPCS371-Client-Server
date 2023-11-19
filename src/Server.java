import java.net.*;
import java.io.*;

public class Server {
    static Socket socket; // socket class uses TCP/IP
    static InputStreamReader input; // receive data from server
    static OutputStreamWriter output; // send data to server

    // use buffer to speed up operation

    static BufferedReader bufferedReader;
    static BufferedWriter bufferedWriter;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9000); // waits for requests on a certain port number (9000)

        while (true) {
            initServer(serverSocket);
            // second while loop to keep serving client

            while (true) {
                try {
                    // receive input fr om user
                    String string = bufferedReader.readLine();
                    char character = bufferedReader.readLine().charAt(0);

                    System.out.print("String: " + string + ", Character: " + character + "\n"); // print inputs on
                                                                                                // server side

                    int characterCount = countCharacters(string, character); // get character count
                    bufferedWriter.write("Number of Occurrences: " + characterCount); // send character count
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    char userChoice = bufferedReader.readLine().charAt(0); // receive user choice
                    if (userChoice == 'n') { // if user choice == n, break and close server
                        System.out.println("Client Disconnected");
                        break;
                    }
                } catch (SocketException e) {
                    System.err.println("Connection to Client Lost");
                    System.exit(0);
                } catch (NullPointerException e) {
                    System.err.println("Connection to Client Lost");
                    System.exit(0);
                }

            }

            closeServer(serverSocket);
            break;

        }

    }

    private static void initServer(ServerSocket serverSocket) {
        try {
            System.out.println("Server is Up and Listening on port 9000");
            socket = serverSocket.accept();
            System.out.println("Client Connected!");
            input = new InputStreamReader(socket.getInputStream());
            output = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(input);
            bufferedWriter = new BufferedWriter(output);
        } catch (SocketException e) {
            System.err.println("Connection to Client Lost");
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private static void closeServer(ServerSocket serverSocket) {
        try {
            socket.close();
            input.close();
            output.close();
            bufferedReader.close();
            bufferedWriter.close();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private static int countCharacters(String string, char character) {
        int count = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character)
                count++;
        }
        return count;
    }
}
