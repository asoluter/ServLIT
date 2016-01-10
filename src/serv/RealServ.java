package serv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serv.DataControls.DataForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RealServ extends JFrame {


    static Logger logger;

    public RealServ(){
        start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        new RealServ().setVisible(true);
                    }
                }
        );
    }

    JButton startButton;
    JButton stopButton;
    JButton editDataButton;
    DataForm dataForm;

    public void start(){
        dataForm=new DataForm();

        setTitle("ServLIT");
        setSize(700,500);
        JPanel root=new JPanel(new BorderLayout());
        getContentPane().add(root);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        JPanel panel=new JPanel(new BorderLayout());
        JPanel bpanel=new JPanel(new FlowLayout());

        bpanel.setSize(200,500);
        panel.setSize(500,500);

        startButton=new JButton("Start");
        startButton.addActionListener(e -> startServ());
        stopButton=new JButton("Stop");
        stopButton.addActionListener(e -> stopServ());
        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        editDataButton =new JButton("Edit database");
        editDataButton.addActionListener(e -> startEditDataForm());

        bpanel.add(startButton);
        bpanel.add(stopButton);
        bpanel.add(editDataButton);
        panel.add(bpanel,BorderLayout.CENTER);
        bpanel.setPreferredSize(new Dimension(200,500));

        JTextArea area=new JTextArea();
        area.setEditable(false);

        PrintStream printStream=new PrintStream(new CustomOutputStream(area));
        System.setOut(printStream);
        System.setErr(printStream);
        JScrollPane scrollPane=new JScrollPane(area,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        root.add(panel,BorderLayout.WEST);
        root.add(scrollPane,BorderLayout.CENTER);

        logger=LogManager.getLogger("Server");

    }

    private void startEditDataForm() {
        dataForm.setVisible(true);
    }

    private Thread serverThread;
    private ServerSocket serverSocket;
    private boolean serverRun;
    public void startServ(){
        serverRun=false;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        serverThread=new Thread(() -> {
            new Refresh().start();
            try {
                serverSocket=new ServerSocket(8000);

                logger.info("started");

                try {
                    while (!Thread.interrupted()){
                        Socket socket=serverSocket.accept();
                        logger.info("connection attempt");
                        new Handler(socket).start();
                    }
                }finally {
                    serverSocket.close();
                }
            } catch (IOException e) {
                if (serverRun)logger.error("fail on start");
                e.printStackTrace();
                serverRun=false;
            }
        });

        serverThread.start();

    }

    public void stopServ(){
        try {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            serverRun=false;
            serverThread.stop();
            serverSocket.close();

            logger.info("server stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
