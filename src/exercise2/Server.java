package exercise2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket server = null;

    private BufferedReader in = null;
    PrintWriter out;
    exercise2.Message message;
    public Socket s;
    private final int min = 0;
    private final int max = 1000;

    public Server(int port) throws IOException {
        List<Integer> list_clients = new ArrayList<Integer>();
        try {

            server = new ServerSocket(port);
            System.out.println("Server started");
            exercise2.OperatorThread operator = new exercise2.OperatorThread();

            operator.start();
            while (true) {

                s = server.accept();

                //message = Message.newBuilder().build();
                /*
                int id;
                id = Integer.parseInt(in.readLine());;

                while (list_clients.contains(id)) {
                    out.println("Insert a new id, this one is already present");
                    id = Integer.parseInt(in.readLine());;
                }
                list_clients.add(id);*/
                System.out.println("connection Established");
                //System.out.println("Just connected with the id client " + id);
                Thread r = new exercise2.HandlingClientThread(s, operator);
                r.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: server [port]");
            System.exit(0);
        }
        Server server = new Server(Integer.parseInt(args[0]));

        System.out.println("Server shutting down...");

    }

}
