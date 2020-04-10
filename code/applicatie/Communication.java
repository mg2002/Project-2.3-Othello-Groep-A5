package code.applicatie;

import code.applicatie.command.*;
import code.applicatie.command.GameStart;

import java.io.*;
import java.net.Socket;

/**
 * Class Communication
 * Communicates with applicatie. Sends commands to applicatie such as logIn, subscribe, forfeit, doMove.
 * For receiving information from the applicatie, it has a 'decision' tree called awaitServerCommand.
 * <p>
 * String serverHost = IP address of applicatie to connect to
 * int port = port of applicatie to connect to
 * Socket socket = socket of applicatie we want to connect to
 * PrintWriter printWriter = sending information to the applicatie
 * BufferedReader bufferedReader = receiving information from the applicatie
 *
 * @author Merel Foekens
 * @version 1.2
 */
public class Communication {
    private static final String serverHost = "145.33.225.170";
    private static final int port = 7789;
    private final Socket socket = new Socket(serverHost, port);
    private PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    public Communication() throws IOException {
        // Eerste twee regels zijn copyright en nog iets randoms dus dat kan wel hardcoded.
        readLine();
        readLine();
    }

    /**
     * Parses lines from the applicatie and puts useful information in a ServerCommand Object.
     *
     * @return an object with information that was read
     * @throws IOException when third word is unknown
     */
    public ServerCommand awaitServerCommand() throws IOException {
        var data = readLine();
        if (!data.startsWith("SVR GAME")) {
            throw new IllegalStateException("Onverwacht bericht van applicatie :( " + data);
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
                case "CHALLENGE":
                    String challenger = args[1];
                    String challengeNum = args[3];
                    gameName = args[5];
                    return new ChallengeReceived(challenger, challengeNum, gameName);
                case "WIN":
                case "DRAW":
                case "LOSS":
                    String playerOneScore = args[1];
                    String playerTwoScore = args[3];
                    String comment = args[5];
                    boolean hasWon = command.equals("WIN");
                    return new GameEnd(playerOneScore, playerTwoScore, comment, hasWon);
                default:
                    throw new IllegalStateException("No code.applicatie.command found " + data);
            }
        }
    }

    /**
     * @param playerName name of the player that wishes to play.
     * @return returns whether login was successful
     */
    public boolean logIn(String playerName) throws IOException {
        String sendMessage = String.format("login %s", playerName);
        writeLine(sendMessage);
        return readLine().equals("OK");
    }

    /**
     * Logs out and disconnects from the applicatie.
     */
    public void logOut() {
        writeLine("logout");
    }

    /**
     * @param gameName Name of the game a user wishes to subscribe to
     * @return returns whether subscribing to a game has been successful
     */
    public boolean subscribe(String gameName) throws IOException {
        String sendMessage = String.format("subscribe %s", gameName);
        writeLine(sendMessage);
//        return readLine().equals("OK");
        return true;
    }

    /**
     * @return returns whether player forfeited (returns false if player is not in game)
     */
    public boolean forfeit() throws IOException {
        writeLine("forfeit");
        return readLine().equals("OK");
    }

    /**
     * @param pos where player wants to place their move
     * @return returns whether move was accepted
     */
    public boolean doMove(int pos) throws IOException {
        String sendMessage = String.format("move %s", pos);
        writeLine(sendMessage);
        return true;
    }

    /**
     * @param playerToChallenge name of the player you want to challenge
     * @param gameName          name of the game you want to play
     * @return whether challenge was successful
     */
    public boolean sendChallenge(String playerToChallenge, String gameName) throws IOException {
        writeLine(String.format("challenge \"%s\" \"%s\"", playerToChallenge, gameName));
        String data = readLine();
        if (data.equals("OK")) {
            return true;
        } else {
            var args = data.split(" ");
            if (args[2].equals("player:")) {
                System.out.println("Onbekende speler");
                return false;
            } else if (args[2].equals("game:")) {
                System.out.println("Onbekend spel");
                return false;
            }
        else throw new IllegalStateException("Error: " + data);
        }
    }

    /**
     * @param challengeNum number of the challenge you want to accept
     * @return whether successful challenge number was sent
     */
    public boolean challengeAccept(String challengeNum) throws IOException {
        writeLine(String.format("challenge accept %s", challengeNum));
        return readLine().equals("OK");
    }

    /**
     * get the list of possible games and logged in players
     *
     * @param listToGet either playerlist or gamelist
     */
    public void getList(String listToGet) throws IOException {
        writeLine(String.format("get %s", listToGet));
        if (readLine().equals("OK")) {
            readLine();
        } else {
            throw new IllegalStateException("Opgevraagde lijst bestaat niet");
        }
    }

    /**
     * @param data send data to applicatie
     */
    private void writeLine(String data) {
        System.out.println("[SEND] " + data);
        printWriter.println(data);
    }

    /**
     * @return receive data from applicatie
     */
    private String readLine() throws IOException {
        var data = bufferedReader.readLine();
        System.out.println("[RECEIVE] " + data);
        return data;
    }
}
