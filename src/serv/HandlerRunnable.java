package serv;

import com.asoluter.litest.Objects.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import serv.DataQuery.MDB;
import serv.Tests.Tests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
                RegData regData=(RegData) obj.getObject();

                if(MDB.checkRegister(regData))out.writeObject(new TypingObject(Strings.OK, new NullObject()));
                else out.writeObject(new TypingObject(Strings.BAD,new NullObject()));

            }else if (obj.getType().equals(Strings.AUTH)) {
                auth=(AuthObject)obj.getObject();
                authorized=MDB.checkPass(auth);
                if(authorized){
                    out.writeObject(new TypingObject(Strings.OK,new NullObject()));
                }
                else {
                    out.writeObject(new TypingObject(Strings.BAD,new NullObject()));
                }
            }else if(obj.getType().equals(Strings.TEST)){
                Pair pair=(Pair)obj.getObject();
                auth=(AuthObject)pair.getFirst();
                int Iauthorized=MDB.IcheckPass(auth);
                if(Iauthorized!=-1){
                    String login=auth.getUserName();
                    ArrayList<AnsObject> ansvers=(ArrayList)pair.getSecond();
                    int kRans=MDB.IcheckAnsvers(MDB.IgetU_id(login),ansvers);
                    out.writeObject(new TypingObject(Strings.TEST_RESULT,kRans));

                }
                else {
                    out.writeObject(new TypingObject(Strings.TEST_RESULT,-1));
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
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                authorized=false;
                logger.info("disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

