package exercise1;
import javax.swing.*;
import java.awt.*;

public class OperatorThread extends Thread{
    JTextField txt;
    int num_clients=0;
    JFrame frame;
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
    public synchronized void oneMoreClient(){
        this.num_clients++;
        System.out.println("adding new client ");
        //frame.getContentPane().removeAll();

        txt.setText("clients connected: "+this.num_clients+"  ");

        //frame.add(txt);
        //SwingUtilities.updateComponentTreeUI(frame);
        //frame.revalidate();

        //frame.revalidate();
        frame.repaint();
    }
    public synchronized void oneLessClient(){
        this.num_clients--;

        System.out.println("removing one client ");

        txt.setText("clients connected: "+this.num_clients+"  ");


        //frame.revalidate();
        //frame.add(txt);
        //SwingUtilities.updateComponentTreeUI(frame);
        //frame.revalidate();
        frame.repaint();

    }
}
