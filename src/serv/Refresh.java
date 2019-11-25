package serv;

import com.asoluter.litest.Objects.DataBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serv.DataQuery.MDB;

public class Refresh extends Thread {

    @Override
    public void run() {
        new RefreshRun().run();
    }

    private class RefreshRun implements Runnable {
        Logger logger= LogManager.getLogger("Refresher");

        @Override
        public void run() {
            while (true){
                try {
                    synchronized (this){
                        logger.info("refresh started");
                        MDB.refresh();
                        logger.info("data refreshed");
                        wait(300000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
