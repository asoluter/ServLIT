package serv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serv.Auth.MDB;
import serv.Objects.AuthObject;
import serv.Objects.Strings;
import serv.Objects.TestObject;
import serv.Objects.TypingObject;

import java.io.*;
import java.net.Socket;

public class Handler extends Thread {
    Logger logger= LogManager.getLogger("New Connection");

    private String name;
    private Socket socket;
    private boolean authorized=false;
    private AuthObject auth;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Handler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {

        logger.info("Made it");

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

            }
            //TODO: DELETE THAT SHIT
            authorized=false;
            TypingObject test=new TypingObject("test",new TestObject("vyshlo"));
            out.writeObject(test);
            /** */

            while (authorized){

                TypingObject object=(TypingObject)in.readObject();

                switch (object.getType()){

                    case Strings.EXIT:{
                        authorized=false;
                        break;
                    }

                    case Strings.TEST:{
                        //TODO:test system
                        break;
                    }

                    case Strings.PUZZLE:{
                        //TODO:make puzzles
                        break;
                    }

                    /** cases */

                }

            }

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

