package exercise2;

import java.io.IOException;
import java.util.ArrayList;

public class MultiClients {
    int num_clients=1;
    String ip;
    int port;
    ArrayList<ClientThread> list_clients;
    public MultiClients(String ip, int port, int num_clients) throws IOException {
        this.num_clients = num_clients;
        list_clients = new ArrayList<ClientThread>();
        for(int i=0; i< num_clients; i++){
            ClientThread r = new ClientThread(ip, port);
            list_clients.add(r);
            r.start();
        }
        for(client in list_clients){

            r.start();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: server [ip][port][num clients]");

            System.exit(0);
        }else{
            MultiClients multiClients = new MultiClients(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }


    }
}
