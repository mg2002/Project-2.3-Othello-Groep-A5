package code.applicatie;

import java.io.*;
import java.net.Socket;

public class Communication {
    private final String serverHost = "145.33.225.170";
    private final int port = 7789;
    private final Socket socket = new Socket(this.serverHost, this.port);
    private PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    public Communication() throws IOException, InterruptedException {
        // Eerste twee regels zijn copyright en nog iets randoms dus dat kan wel hardcoded hihi
        System.out.println(bufferedReader.readLine());
        System.out.println(bufferedReader.readLine());
//        while(true){
//            System.out.println(bufferedReader.ready());
//            Thread.sleep(100);
//        }
        System.out.println(login("klees"));
        System.out.println(subscribe("Tic-tac-toe"));
        gameStart();
        gameYourTurn();
        doMove(1);
        getMove();
    }

    /**
     * @param playerName
     * @return returns whether login was successful
     * @throws IOException
     */
    public boolean login(String playerName) throws IOException {
        String sendMessage = String.format("login %s", playerName);
        writeLine(sendMessage);
        return readLine().equals("OK");
    }

    /**
     * @param gameName
     * @return returns whether subscribing to a game has been successful
     * @throws IOException
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

    public GameStart gameStart() throws IOException, IllegalStateException {
        var data = readLine();
        if (!data.startsWith("SVR GAME MATCH")) {
            throw new IllegalStateException("Not in a match: " + data);
        } else {
            var j = data.split("\"");
            //alle array posities zijn hetzelfde dus mag hardcode
            String playerToMove = j[1];
            String gameName = j[3];
            String opponent = j[5];
            return new GameStart(playerToMove, gameName, opponent);
        }
    }

    public String gameYourTurn() throws IOException {
        var data = readLine();
        if (!data.startsWith("SVR GAME YOURTURN")) {
            throw new IllegalStateException("stuk" + data);
        } else {
            var j = data.split("\"");
            return j[1];
        }
    }

    public GetMove getMove() throws IOException {
        var data = readLine();
        if (!data.startsWith("SVR GAME MOVE")) {
            throw new IllegalStateException("Stuk " + data);
        } else {
            var j = data.split("\"");
            String playerName = j[1];
            String move = j[3];
            String details = j[5];
            return new GetMove(playerName, move, details);
        }
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
