import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class chatServer implements ActionListener, KeyListener {
    public static ServerSocket ss;
    public static Socket s;
    public static DataInputStream dis;
    public static DataOutputStream dos;

    public static JFrame f;
    public JButton send;
    public JTextField t;
    public JLabel head;
    public JLabel msg;
    public static JLabel swingmsg;
    public static JTextArea ta;
    public JScrollPane sp;

    public chatServer() {
        guibuild();
    }

    private void guibuild() {

        f = new JFrame("Server Side");
        t = new JTextField();
        send = new JButton("Send");
        ta = new JTextArea();

        head = new JLabel("SERVER", JLabel.CENTER);
        head.setVerticalAlignment(JLabel.TOP);

        msg = new JLabel("Enter Your Message : ");

        swingmsg = new JLabel("Client Found!", JLabel.CENTER);
        swingmsg.setVerticalAlignment(JLabel.BOTTOM);

        sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        t.setBounds(30, 100, 280, 40);
        t.setFont(new Font("Arial", Font.BOLD, 17));

        send.setBounds(350, 100, 70, 40);
        send.setFont(new Font("Arial", Font.BOLD, 14));

        ta.setBounds(30, 180, 390, 360);
        ta.setFont(new Font("Arial", Font.BOLD, 17));

        head.setFont(new Font("Arial", Font.BOLD, 17));
        head.setBounds(175, 10, 70, 40);
        head.setForeground(Color.red);

        msg.setBounds(30, 60, 280, 40);
        msg.setFont(new Font("Arial", Font.BOLD, 17));

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

    public void actionPerformed(ActionEvent e) {
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

        new chatServer();
        String msg = "";
        try {
            ss = new ServerSocket(6666);
            s = ss.accept();

            f.add(swingmsg);
            swingmsg.setBounds(110, 545, 200, 40);
            swingmsg.setFont(new Font("Arial", Font.BOLD, 17));
            swingmsg.setForeground(Color.BLUE);

            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            while (!msg.equals("exit")) {
                msg = dis.readUTF();
                ta.setText("Client : " + msg + "\n");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}