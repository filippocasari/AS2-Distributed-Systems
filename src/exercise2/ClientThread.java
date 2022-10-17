package exercise2;

import java.io.IOException;

public class ClientThread extends Thread{
    String ip;
    int port;

    /**
     * Create a client thread.
     * <p>
     * 2022-10-17
     */
    public ClientThread(String ip, int port) {
        this.ip =ip;
        this.port=port;

    }

    /**
     * Run the server.
     * <p>
     *
     * 2022-10-17
     */
    public void run() {
        Client2 client;
        try {
            client = new Client2(this.ip, this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.readServerResponse();
    }
}
