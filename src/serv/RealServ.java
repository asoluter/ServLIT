package serv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RealServ {
    static Logger logger= LogManager.getLogger("Server");

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8000);

            logger.info("started");

            try {
                while (true){
                    Socket socket=serverSocket.accept();
                    logger.info("connection attempt");
                    new Handler(socket).run();
                }
            }finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            logger.error("fail on start");
            e.printStackTrace();
        }
    }

}
