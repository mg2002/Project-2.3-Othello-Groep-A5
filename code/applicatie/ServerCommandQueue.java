package code.applicatie;

import code.applicatie.command.ServerCommand;

import java.io.IOException;
import java.util.Queue;

public class ServerCommandQueue implements Runnable{
    private boolean readFromServer = true;
    private Communication communication;
    private  Queue<ServerCommand> queue;
    private Thread communicationThread;

    public ServerCommandQueue(Queue<ServerCommand> queue) throws IOException {
        this.queue = queue;
        this.communication = new Communication();
        this.communicationThread = new Thread(this);
        communicationThread.run();
    }

    public void queueCommand() throws IOException {
        while (readFromServer){
            queue.add(communication.awaitServerCommand());
        }
    }

    @Override
    public void run() {
        try {
            queueCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReadFromServer() {
        return readFromServer;
    }

    public void setReadFromServer(boolean readFromServer) {
        this.readFromServer = readFromServer;
    }
}
