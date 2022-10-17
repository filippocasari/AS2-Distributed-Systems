package exercise2;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Client2 {

    private final String ip;
    private final int port;
    private OutputStream os;
    private CodedInputStream is;
    Socket socket;
    exercise2.Message message;
    int name_client;

    public Client2(String ip, int port) throws IOException {
        this.port = port;
        this.name_client = ThreadLocalRandom.current().nextInt(1, 1000 + 1);
        this.ip = String.valueOf(ip);


    }

    public void readServerResponse() {
        try {
            socket = new Socket(this.ip, this.port);
            is = CodedInputStream.newInstance(this.socket
                    .getInputStream());
            os = (this.socket.getOutputStream());
            System.out.println("Client is trying to connect to " + this.ip + ":" + this.port);
            Scanner scanner = new Scanner(System.in);
            String inputString = "";

            while (!inputString.equals("end")) {
                String to;
                System.out.println("insert the receiver");
                to = scanner.nextLine();
                System.out.println("insert the message");
                inputString = scanner.nextLine();
                message = exercise2.Message.newBuilder().setFr(this.name_client).setTo(Integer.parseInt(to)).setMsg(inputString).build();
                //System.out.println("input string: " + inputString);
                //os.writeBytes(inputString + "\n");
                //os.flush();
                byte[
                        ]message_byte=message.toByteArray();
                os.write(message_byte);

                os.flush();

                //os.write(message.toString().getBytes());
                //System.out.println(is.readLine());
                //System.out.println("Insert another string");

            }


        } catch (Exception e) {
            System.err.println("something went wrong");
        }

    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: server [ip][port]");
            System.exit(0);
        }
        Client2 client = new Client2(args[0], Integer.parseInt(args[1]));
        client.readServerResponse();

        System.out.println("Server shutting down...");


    }
}
