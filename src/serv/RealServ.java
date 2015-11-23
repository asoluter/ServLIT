package serv;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

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

    public void start(){
        setTitle("ServLIT");
        setSize(700,500);
        JPanel root=new JPanel(new BorderLayout());
        getContentPane().add(root);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel=new JPanel(new BorderLayout());
        JPanel bpanel=new JPanel(new FlowLayout());

        panel.setSize(200,500);
        panel.setSize(500,500);

        startButton=new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServ();
            }
        });
        stopButton=new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServ();
            }
        });
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        bpanel.add(startButton);
        bpanel.add(stopButton);
        panel.add(bpanel,BorderLayout.CENTER);

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

    private Thread serverThread;
    private ServerSocket serverSocket;
    private boolean serverRun;
    public void startServ(){
        serverRun=false;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        serverThread=new Thread(new Runnable() {
            @Override
            public void run() {
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
