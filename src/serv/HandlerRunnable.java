package serv;

import com.asoluter.litest.Objects.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serv.DataQuery.MDB;
import serv.Tests.Tests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlerRunnable implements Runnable {
    Logger logger= LogManager.getLogger("New Connection");

    private String name;
    private Socket socket;
    private boolean authorized=false;
    private AuthObject auth;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public HandlerRunnable(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {

            logger.info("connected");

            try {
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
            TypingObject obj=(TypingObject)in.readObject();

            if (obj.getType().equals(Strings.REGISTER)){
                auth=(AuthObject)obj.getObject();
                //TODO: make registration


            }
            if (obj.getType().equals(Strings.AUTH)) {
                auth=(AuthObject)obj.getObject();
                authorized=MDB.checkPass(auth);
                if(authorized){
                    out.writeObject(new TypingObject(Strings.OK,new NullObject()));
                }
                else {
                    out.writeObject(new TypingObject(Strings.BAD,new NullObject()));
                }
            }
            /**TODO: DELETE THAT
            authorized=false;
            TypingObject test=new TypingObject("test",new TestObject("vyshlo"));
            out.writeObject(test);
            */
            if(obj.getType().equals(Strings.REFRESH)){
                TypingObject sendData=new TypingObject(Strings.DATABASE, Tests.getDataBase());
                out.writeObject(sendData);
            } /*else
            while (authorized){

                TypingObject object=(TypingObject)in.readObject();

                switch (object.getType()){

                    case Strings.EXIT:{
                        authorized=false;
                        logger.info("disconnected");
                        break;
                    }

                    case Strings.REFRESH:{
                        TypingObject sendData=new TypingObject(Strings.DATABASE, Tests.getDataBase());
                        out.writeObject(sendData);
                        break;
                    }

                    case Strings.TEST_RESULT:{

                        break;
                    }



                }

            }*/

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

