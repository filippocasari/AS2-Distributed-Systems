package exercise1;

import java.io.IOException;

public class MultiClients {
    int num_clients=1;
    String ip;
    int port;
    public MultiClients(String ip, int port, int num_clients) throws IOException {
        this.num_clients = num_clients;

        for(int i=0; i< num_clients; i++){
            Thread r = new ClientThread(ip, port);
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
