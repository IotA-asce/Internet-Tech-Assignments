import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;
    final String USERNAME = "admin";
    final String PASSWORD = "admin";

    private boolean isUsernameCorrect = false;

    Integer statusFlag = 0; /*
                             * Indicates type of client
                             * Available Flags :
                             * 0 => normal
                             * 1 => admin
                             */

    public static ServerMemory serverMemory = new ServerMemory();

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {

        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run() {

        String received;
        while (true) {

            try {
                dataOutputStream.writeUTF(
                        "Type in the script...\n" +
                                "Type exit to terminate connection.\n" +
                                "****************************************\n");

                received = dataInputStream.readUTF();

                if (received.equals("exit")) {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                } else if (received.equals("status")) {
                    System.out.println("client from port => " + this.socket.getPort() + " query => Status report");

                    System.out.println(
                            serverMemory.MEMORY_STATUS(statusFlag));
                    dataOutputStream.writeUTF(
                            serverMemory.MEMORY_STATUS(statusFlag));
                    continue;
                }

                ArrayList<String> strings = new ServerUtil().stringTokens(received);
                if (strings.isEmpty()) {
                    dataOutputStream.writeUTF("\tErrorneous Input");
                    continue;
                }
                String responce = "\n";
                while (!strings.isEmpty()) {

                    String command = strings.get(0).toUpperCase();
                    switch (command) {

                        case "GET":
                            System.out.println("client from port => " + this.socket.getPort() + " query => "
                                    + strings.get(0) + " " + strings.get(1));

                            System.out.println(serverMemory.GET(strings.get(1)));
                            responce += serverMemory.GET(strings.get(1)) + "\n";
                            strings.remove(0);
                            strings.remove(0);
                            break;
                        case "PUT":
                            System.out.println("client from port => " + this.socket.getPort() + " query => "
                                    + strings.get(0) + " " + strings.get(1) + " " + strings.get(2));

                            String messege = serverMemory.PUT(strings.get(1), strings.get(2));
                            System.out.println(messege);
                            responce += messege + "\n";
                            strings.remove(0);
                            strings.remove(0);
                            strings.remove(0);
                            break;
                        case "DELETE":
                            System.out.println("client from port => " + this.socket.getPort() + " query => "
                                    + strings.get(0) + " " + strings.get(1));
                            String messege2 = serverMemory.DELETE(strings.get(1), statusFlag);
                            System.out.println(messege2);
                            responce += messege2 + "\n";
                            strings.remove(0);
                            strings.remove(0);
                            break;
                        case "PASSWORD":
                            // System.out.println("client from port => " + this.socket.getPort() + "query => "
                            //         + strings.get(0) + " " + strings.get(1));

                            if (strings.get(1).equals(PASSWORD)) {
                                statusFlag = 1;
                                responce += "\tAdmin access granted..." + "\n";
                            } else {
                                responce += "\tIncorrect Password" + "\n";
                            }
                            strings.remove(0);
                            strings.remove(0);
                            break;
                        default:
                            System.out.println(
                                    "client from port => " + this.socket.getPort() + "query => " + strings.get(0));

                            responce += "Invalid input\n";
                            break;

                    }
                }
                dataOutputStream.writeUTF(responce);

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        try {

            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
