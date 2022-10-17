package exercise2;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.*;
import java.net.Socket;

public class HandlingClientThread extends Thread {

    //private int id = 0;


    String line = null;
    InputStream input ;
    OutputStream output ;
    Socket s;
    OperatorThread operatorThread;
    Message message;

    public HandlingClientThread(Socket socket, OperatorThread operatorThread) {
        this.s = socket;
        //this.id = id;
        this.operatorThread = operatorThread;
    }

    public void run() {
        operatorThread.oneMoreClient();
        System.out.println("starting thread to handle client");
        try {
            /*is = CodedInputStream.newInstance(this.s
                    .getInputStream());*/
            this.input = this.s.getInputStream();
            this.output = this.s.getOutputStream();

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        try {


            message = Message.parseFrom(this.input);
            System.out.println("waiting message...");
            int from = message.getFr();
            int to = message.getTo();
            String msg = message.getMsg();

            Message reply ;
            while (msg.compareTo("end") != 0) {
                System.out.println("client " + from+ " said: " + msg+ " to "+to);
                reply = Message.newBuilder().setFr(1).setTo(from).setMsg(msg).build();
                //reply.writeTo(os);
                //os.flush();
                message = Message.parseFrom(this.input);
                from = message.getFr();
                to = message.getTo();
                msg = message.getMsg();

            }
        } catch (IOException e) {

            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } finally {
            operatorThread.oneLessClient();
            System.out.println("Connection Closing..");


        }//end finally
    }


}
