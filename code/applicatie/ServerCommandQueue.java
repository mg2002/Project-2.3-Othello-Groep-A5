package code.applicatie;

import code.applicatie.command.ServerCommand;

import java.io.IOException;
import java.util.LinkedList;

public class ServerCommandQueue implements Runnable {
    private boolean readFromServer = false;
    private Communication communication;
    private LinkedList<ServerCommand> linkedList;
    private Thread communicationThread;

    public ServerCommandQueue(LinkedList<ServerCommand> linkedList) throws IOException {
        this.linkedList = linkedList;
        this.communication = new Communication();
        this.communicationThread = new Thread(this);
        communicationThread.start();
    }

    public void queueCommand() throws IOException {
        while (readFromServer) {
            System.out.println("hallo");
            linkedList.add(communication.awaitServerCommand());
        }
    }

    public ServerCommand getServerCommand() {
        while (true) {
            if (linkedList.size() > 0) {
                ServerCommand serverCommandTemp = linkedList.getFirst();
                linkedList.removeFirst();
                return serverCommandTemp;
            }
        }
    }

    @Override
    public void run() {
        try {
            queueCommand();
            System.out.println("hoi");
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
