package exercise2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Client2 {

    private final String ip;
    private final int port;
    private OutputStream os;
    private InputStream is;
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
            is = (this.socket
                    .getInputStream());
            os = (this.socket.getOutputStream());
            System.out.println("Client is trying to connect to " + this.ip + ":" + this.port);

            // Handshake process
            try{
                MessageHandShake messageHandshake = MessageHandShake.newBuilder().setId(this.name_client).setError(false).build();
                messageHandshake.writeDelimitedTo(os);
                os.flush();
                MessageHandShake reply_HandShake = MessageHandShake.parseDelimitedFrom(this.is);
                boolean error = reply_HandShake.getError();
                if(error){
                    System.err.println("error while establishing handshake");
                    System.err.println("Probably this id is already used");
                    return ;
                }
                else {
                    System.out.println("ACK from server:\n"+reply_HandShake);
                }
            }catch (Exception e){
                System.err.println("error while establish connection"+ e);
                return ;

            }



            Scanner scanner = new Scanner(System.in);
            String inputString = "";
            String msg;
            while (!inputString.equals("end")) {
                String to;
                System.out.println("Hey client "+this.name_client+" insert the receiver");
                to = scanner.nextLine();
                System.out.println("Hey client "+this.name_client+" insert the message");
                inputString = scanner.nextLine();
                message = exercise2.Message.newBuilder().setFr(this.name_client).setTo(Integer.parseInt(to)).setMsg(inputString).build();
                //System.out.println("input string: " + inputString);
                //os.writeBytes(inputString + "\n");
                //os.flush();
                message.writeDelimitedTo(os);
                os.flush();
                Message reply_from_server = Message.parseDelimitedFrom(this.is);
                int from_who = reply_from_server.getFr();
                //to = reply_from_server.getTo();
                msg = reply_from_server.getMsg();
                if(from_who == 1) {
                    System.out.println("message from the server: " + msg);
                }
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
