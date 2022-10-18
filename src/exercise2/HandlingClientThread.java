package exercise2;

import java.io.*;
import java.net.Socket;

public class HandlingClientThread extends Thread {

    public final int id_server = 1;
    String line = null;
    InputStream in ;
    OutputStream out ;
    Socket s;
    OperatorThread operatorThread;
    Message message;

    /**
     * Set the client thread.
     * <p>
     *
     * 2022-10-17
     */
    public HandlingClientThread(Socket socket, OperatorThread operatorThread) {
        this.s = socket;

        //this.id = id;
        this.operatorThread = operatorThread;
    }

    /**
     * Run thread.
     * <p>
     *
     * 2022-10-17
     */
    public void run() {

        System.out.println("starting thread to handle client");
        try {

            this.in = this.s.getInputStream();
            this.out = this.s.getOutputStream();

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        MessageHandShake messageHandShake;
        try {
            messageHandShake = MessageHandShake.parseDelimitedFrom(this.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int id_client = messageHandShake.getId();
        try {

            MessageHandShake messageHandShake_response;
            boolean isNotContainedAlready = operatorThread.oneMoreClient(id_client);
            if(!isNotContainedAlready){
                messageHandShake_response= MessageHandShake.newBuilder().setError(true).setId(this.id_server).build();
                messageHandShake_response.writeDelimitedTo(out);
                System.err.println("this id is already used");
                return;
            }else{

                messageHandShake_response= MessageHandShake.newBuilder().setError(false).setId(id_client).build();
                messageHandShake_response.writeDelimitedTo(out);
            }
            message = Message.parseDelimitedFrom(this.in);
            System.out.println("waiting message...");
            int from = message.getFr();
            int to = message.getTo();
            String msg = message.getMsg();

            Message reply;
            while (msg.compareTo("end") != 0) {

                System.out.println("client " + from+ " replied to client: " +to+ ": "+ msg);
                reply = Message.newBuilder().setMsg(msg).setFr(this.id_server).setTo(from).build();
                reply.writeDelimitedTo(out);
                out.flush();

                message = Message.parseDelimitedFrom(this.in);
                from = message.getFr();
                to = message.getTo();
                msg = message.getMsg();

            }
            //this.list_clients.remove(id_client);
        } catch (IOException e) {

            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } finally {
            operatorThread.oneLessClient(id_client);
            System.out.println("Connection Closing..");

        }//end finally
    }



}
