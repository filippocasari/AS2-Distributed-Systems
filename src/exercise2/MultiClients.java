package exercise2;

import java.util.ArrayList;


public class MultiClients {
    int num_clients;

    ArrayList<ClientThread> list_clients;

    /**
     * Create new multiclient instance .
     * <p>
     *
     * 2022-10-17
     */
//Semaphore mutex=new Semaphore(1);
    public MultiClients(String ip, int port, int num_clients) throws InterruptedException {
        this.num_clients = num_clients;
        list_clients = new ArrayList<ClientThread>();
        for(int i=0; i< num_clients; i++){
            ClientThread r = new ClientThread(ip, port);
            list_clients.add(r);
            r.start();
        }
        for (ClientThread list_client : list_clients) {
            list_client.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            System.out.println("Usage: server [ip][port][num clients]");

        }else{
            MultiClients multiClients = new MultiClients(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }


    }
}
