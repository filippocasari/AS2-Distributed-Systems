package src.exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandlingClientThread extends Thread {

    private int id = 0;


    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;
    Socket s = null;
    OperatorThread operatorThread;

    public HandlingClientThread(Socket socket, int id, OperatorThread operatorThread) {
        this.s = socket;
        this.id = id;
        this.operatorThread = operatorThread;
    }

    public void run() {
        operatorThread.oneMoreClient();
        System.out.println("starting thread to handle client");
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        try {

            line = is.readLine();

            while (line.compareTo("end") != 0) {
                System.out.println("client " + id + " said: " + line);
                os.println("Server here. ACK\n");
                os.flush();

                line = is.readLine();
            }
        } catch (IOException e) {

            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } finally {
            operatorThread.oneLessClient();
            try {
                System.out.println("Connection Closing..");
                if (is != null) {
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if (os != null) {
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s != null) {
                    s.close();
                    System.out.println("Socket Closed");
                }

            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }//end finally
    }


}
