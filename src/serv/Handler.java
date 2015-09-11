package serv;


import java.net.Socket;

public class Handler extends Thread {
    Socket socket;

    public Handler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        new HandlerRunnable(socket).run();
    }
}
