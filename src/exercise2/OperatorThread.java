package exercise2;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OperatorThread extends Thread{
    JTextField txt;
    int num_clients=0;
    JFrame frame;
    public List<Integer> list_clients = new ArrayList<Integer>();
    public OperatorThread() {
        frame = new JFrame("Server Operator Thread");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        txt = new JTextField("clients connected: "+num_clients+"  ");
        //mb.add(m1);
        txt.setBackground(Color.black);
        txt.setFont(new Font("verdana", Font.BOLD, 15));
        txt.setForeground(Color.white);
        frame.add(txt);

        //JButton button1 = new JButton("Press");
        //frame.getContentPane().add(button1);
        frame.setVisible(true);

    }
    public synchronized boolean oneMoreClient(int id_client){

        if(list_clients.contains(id_client)){
            return false;
        }
        this.num_clients++;
        list_clients.add(id_client);
        System.out.println("adding new client ");
        //frame.getContentPane().removeAll();

        txt.setText("clients connected: "+this.num_clients+"  ");
        System.out.println("List of clients"+this.list_clients);
        //frame.add(txt);
        //SwingUtilities.updateComponentTreeUI(frame);
        //frame.revalidate();

        //frame.revalidate();
        frame.repaint();
        return true;
    }
    public synchronized void oneLessClient(int client_id){

        try{
            list_clients.remove(client_id);
            this.num_clients--;
            System.out.println("List of clients"+this.list_clients);
        }
        catch (Exception e){
            System.out.println("id not present in the list");

        }

        System.out.println("removing one client ");

        txt.setText("clients connected: "+this.num_clients+"  ");


        //frame.revalidate();
        //frame.add(txt);
        //SwingUtilities.updateComponentTreeUI(frame);
        //frame.revalidate();
        frame.repaint();

    }
}
