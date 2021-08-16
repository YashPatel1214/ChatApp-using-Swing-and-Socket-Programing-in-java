import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class chatClient implements ActionListener , KeyListener {

    //make object of every class needed
    public static Socket s;
    public static DataInputStream dis;
    public static DataOutputStream dos;

    public static JFrame f;
    public JButton send;
    public JTextField t;
    public JLabel head, msg;
    public static JLabel clientmsg;
    public static JTextArea ta;
    public JScrollPane sp;

    //constructor of a class
    public chatClient() {
        //create method
        guibuild();
    }

    //method call
    private void guibuild() {

        //build gui
        f = new JFrame("Client Side");
        t = new JTextField();
        send = new JButton("Send");
        ta = new JTextArea();
        head = new JLabel("CLIENT", JLabel.CENTER);
        head.setVerticalAlignment(JLabel.TOP);

        msg = new JLabel("Enter Your Message : ");
        clientmsg = new JLabel("Server Found!");
        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        t.setBounds(30, 100, 280, 40);
        t.setFont(new Font("Arial",Font.BOLD,17));

        send.setBounds(350, 100, 70, 40);
        send.setFont(new Font("Arial", Font.BOLD, 14));

        ta.setBounds(30, 180, 390, 360);
        ta.setFont(new Font("Arial",Font.BOLD,17));

        head.setFont(new Font("Arial",Font.BOLD,17));
        head.setBounds(170,10,70,40);
        head.setForeground(Color.red);

        msg.setBounds(30, 60, 280, 40);
        msg.setFont(new Font("Arial",Font.BOLD,17));

        sp.setBounds(30, 180, 390, 360);

        f.add(t);
        f.add(send);
        f.add(sp);
        f.add(head);
        f.add(msg);

        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        f.setLayout(null);
        f.setVisible(true);
        f.setSize(470, 650);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        send.addActionListener(this);
        t.addKeyListener(this);

    }

    //action listener of send button
    public void actionPerformed(ActionEvent evt) {
        try{
            String msgout = "";
            msgout = t.getText().trim();
            dos.writeUTF(msgout);
            ta.append("Client : "+msgout+"\n");
            t.setText(" ");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    //key listener of textfield so we can send data on enter key

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            try {
                String msgout = "";
                msgout = t.getText().trim();
                dos.writeUTF(msgout);
                ta.append("Server : " + msgout + "\n");
                t.setText(" ");

            } catch (Exception f) {
                System.out.println(f);
            }
        }
    }
    public void keyReleased(KeyEvent e) {}

    public static void main(String args[]) {

        //Main Method
        new chatClient();
        String msg = "";
        try{

            //provide a connection between server and client
            s = new Socket("localhost", 6666);

            f.add(clientmsg);
            clientmsg.setBounds(150, 555, 200, 40);
            clientmsg.setFont(new Font("Arial", Font.BOLD, 17));
            clientmsg.setForeground(Color.BLUE);

            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            while(!msg.equals("exit")){
                msg = dis.readUTF();
                ta.append("Server : "+msg+"\n");
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}