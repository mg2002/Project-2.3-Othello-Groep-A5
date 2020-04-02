package code.applicatie;

import code.applicatie.command.GetMove;
import code.applicatie.command.*;
import code.applicatie.command.ServerCommand;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    String aiName = "A5";
    Scanner scanner = new Scanner(System.in);
    Communication comm;
    Board board;
    GameType game;
    Player ai, p1, p2;
    ServerCommand cmd ;
    Boolean wait;

    public Game() throws IOException {
        board = new Board();
        game = new GameType();
        comm =  new Communication();

        while(!comm.logIn("A5")){}
        System.out.println("[LOGIN] Logged in");

        wait = true;
        while (wait){
            System.out.println("b");
            cmd = comm.awaitServerCommand();
            if(cmd != null){
                System.out.println(cmd.toString());
                if(cmd instanceof GameStart){
                    assignGame(((GameStart) cmd).getGameName());
                    if(((GameStart) cmd).getPlayerToMove().equals(aiName)){
                        System.out.println(((GameStart) cmd).getPlayerToMove());
                        assignSides("O");
                    }else{
                        assignSides("X");
                    }
                    wait = false;
                }
            }

        }
    }
    public void run(){
        while(!game.getEnd()){
            cmd = null;
            try {
                cmd = comm.awaitServerCommand();

            }catch(Exception e){System.out.println(e.getMessage());}

            try {

                if(cmd instanceof GetMove){
                    System.out.println("[PROCESSING] busy with other's move");
                    if(((GetMove) cmd).getPlayerName().equals(aiName)){

                    }else{
                        if(p2 instanceof Human){
                            board.doMove(Integer.parseInt(((GetMove) cmd).getMove()), p2);
                        }else{
                            board.doMove(Integer.parseInt(((GetMove) cmd).getMove()), p1);
                        }
                    }
                    Thread.sleep(2000);
                }
                else if(cmd instanceof GameStart){
                    System.out.println("[PROCESSING] starting game");
                    if(game instanceof GameType){
                    }
                        board = new Board();
                        assignGame(((GameStart) cmd).getGameName());
                    if(((GameStart) cmd).getPlayerToMove().equals(aiName)){
                        System.out.println(((GameStart) cmd).getPlayerToMove());
                        assignSides("O");
                    }else{
                        assignSides("X");
                    }
                }else if(cmd instanceof YourTurn){
                    System.out.println("[PROCESSING] calculating move");
                    int move;
                    if(p1 instanceof Human){
                        move = p2.getMove();
                        if(move == -1){
                            comm.forfeit();
                        }else{
                            board.doMove(move, p2);

                            while(!comm.doMove(move)){
                                System.out.println("ERROR: did not recieve OK from applicatie after sending move of ai");
                            }
                        }
                    }else{
                        move = p1.getMove();
                        if(move == -1){
                            comm.forfeit();
                        }else{
                            board.doMove(move, p1);

                            while(!comm.doMove(move)){
                                System.out.println("ERROR: did not recieve OK from applicatie after sending move of ai");
                            }
                        }
                    }
                    System.out.println(move);
                }else if(cmd instanceof GameEnd){
                    System.out.println("[PROCESSING] ending game");
                    board = null;
                    System.out.println("What game would you like to play?");
                    wait = true;
                }else {
                    System.out.println(cmd);
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
    }

    public static void main(String[] agrs) throws IOException {
        Game game = new Game();
        while(true) {
            game.run();
        }
    }

    public Boolean assignSides(String side){
        if(side.equals("white") || side.equals("X") || side.equals("x")){
            p1 = ai;
            p2 = new Human(comm);
            System.out.println("AI is white");
        }else if(side.equals("black")|| side.equals("O") || side.equals("o") || side.equals("0")){
            p1 = new Human(comm);
            p2 = ai;
            System.out.println("AI is black");
        }else{
            System.out.println("ERROR IN ASSIGNING SIDES. gave an illegal side argument");
            System.out.println("Given argument: " + side);
            return false;
        }

        board.setPlayerOne(p1);
        board.setPlayerTwo(p2);

        p1.setSide(0);
        p2.setSide(1);
        game.setPlayers(p1, p2);
        p1.setBoard(board);
        p2.setBoard(board);
        System.out.println("[CREATION] assinged sides");
        return true;
    }

    private Boolean assignGame(String selectedGame) throws IOException {
        switch(selectedGame){
            case("Reversi"):
                game = new Reversi(board);
                board.reversi();
                ai = new Ai();

                if(!comm.subscribe("Reversi")){
                    System.out.println("ERROR: did not recieve OK from applicatie after subscribing to a Reversi.");
                }else{
                    System.out.println("[CREATION] created game Reversi");
                }
                break;
            case("t"):
            case("T"):
            case("Tic-tac-toe"):
                game = new TicTacToe(board);
                board.ticTacToe();
                ai = new Tai();
//                try {
//                    Thread.sleep(2000);
//                }catch (Exception e){
//                    System.out.println(e.getMessage());
//                }
                while(!comm.subscribe("Tic-tac-toe")){
                    System.out.println("ERROR: did not recieve OK from applicatie after subscribing to Tic Tac Toe.");
                }
                System.out.println("[CREATION] created game Tic-tac-toe");

                break;
            default:
                System.out.println("ERROR: wrong game selected.");
                return false;
        }
        return true;
    }



}
