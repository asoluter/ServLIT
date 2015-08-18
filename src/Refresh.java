import com.asoluter.litest.Objects.DataBase;
import serv.Auth.MDB;

public class Refresh extends Thread {
    private DataBase dataBase;

    @Override
    public void run() {
        while (true){
            try {
                refresh();
                wait(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void refresh(){

    }


}
