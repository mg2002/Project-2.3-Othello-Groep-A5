package code.applicatie;

import code.applicatie.command.ServerCommand;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ServerCommandQueue implements Runnable{
    private boolean readFromServer = true;
    private Communication communication;
    private LinkedList<ServerCommand> queue;
    private Thread communicationThread;

    public ServerCommandQueue(LinkedList<ServerCommand> queue, Communication comm) throws IOException {
        this.queue = queue;
        this.communication = comm;
    }

    public void queueCommand() throws IOException {
        ServerCommand temp = communication.awaitServerCommand();
        queue.add(temp);
        System.out.println(queue.size());
    }

    @Override
    public void run() {
        while(true) {
            try {
                queueCommand();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public ServerCommand getCommand(){
        ServerCommand temp = null;
        if(queue.size()  >0){
            temp = queue.getFirst();
            queue.remove(temp);
        }
        return temp;
    }

    public boolean isReadFromServer() {
        return readFromServer;
    }
    public void setReadFromServer(boolean readFromServer) {
        this.readFromServer = readFromServer;
    }
}
