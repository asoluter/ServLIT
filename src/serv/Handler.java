package serv;


import java.net.Socket;

public class Handler extends Thread {
    private Socket socket;

    Handler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        new HandlerRunnable(socket).run();
    }
}
