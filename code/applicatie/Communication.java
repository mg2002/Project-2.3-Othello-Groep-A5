package code.applicatie;

import code.applicatie.command.server.*;

import java.io.*;
import java.net.Socket;

public class Communication {
    private final String serverHost = "145.33.225.170";
    private final int port = 7789;
    private final Socket socket = new Socket(this.serverHost, this.port);
    private PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    public Communication() throws IOException {
        // Eerste twee regels zijn copyright en nog iets randoms dus dat kan wel hardcoded hihi
        readLine();
        readLine();
    }

    public ServerCommand awaitServerCommand() throws IOException {
        var data = readLine();
        if (!data.startsWith("SVR GAME")) {
            throw new IllegalStateException("Onverwacht bericht van server :( " + data);
        } else {
            var args = data.split("\"");
            var command = data.split(" ")[2];
            switch (command) {
                case "MATCH":
                    String playerToMove = args[1];
                    String gameName = args[3];
                    String opponent = args[5];
                    return new GameStart(playerToMove, gameName, opponent);
                case "YOURTURN":
                    String turnMessage = args[1];
                    return new YourTurn(turnMessage);
                case "MOVE":
                    String playerName = args[1];
                    String move = args[3];
                    String details = args[5];
                    return new GetMove(playerName, move, details);
                case "WIN":
                case "LOSS":
                    String playerOneScore = args[1];
                    String playerTwoScore = args[3];
                    String comment = args[5];
                    boolean hasWon = command.equals("WIN");
                    return new GameEnd(playerOneScore, playerTwoScore, comment, hasWon);
                default:
                    throw new IllegalStateException("No command found" + data);
            }
        }
    }

    /**
     * @return returns whether login was successful
     */
    public boolean login(String playerName) throws IOException {
        String sendMessage = String.format("login %s", playerName);
        writeLine(sendMessage);
        return readLine().equals("OK");
    }

    /**
     * @return returns whether subscribing to a game has been successful
     */
    public boolean subscribe(String gameName) throws IOException {
        String sendMessage = String.format("subscribe %s", gameName);
        writeLine(sendMessage);
        return readLine().equals("OK");
    }

    public boolean forfeit() throws IOException {
        writeLine("forfeit");
        return readLine().equals("OK");
    }

    public boolean doMove(int pos) throws IOException {
        String sendMessage = String.format("move %s", pos);
        writeLine(sendMessage);
        return readLine().equals("OK");
    }

    private void writeLine(String data) {
        System.out.println("[SEND] " + data);
        printWriter.println(data);
    }

    private String readLine() throws IOException {
        var data = bufferedReader.readLine();
        System.out.println("[RECEIVE] " + data);
        return data;
    }
}
