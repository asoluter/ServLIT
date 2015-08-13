package serv;

import serv.Auth.MDB;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class RealServ {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8080);
            MDB.init();
            try {
                while (true){
                    new Handler(serverSocket.accept()).run();
                }
            }finally {
                serverSocket.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
