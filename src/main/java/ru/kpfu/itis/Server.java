package ru.kpfu.itis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private PrintStream out;
    private BufferedReader in;
    private Socket clientSocket;
    private Scanner scanner;

    public void run() throws IOException {
        serverSocket = new ServerSocket(9999);
        clientSocket = serverSocket.accept();

        System.out.println("YOU'RE PLAYING FOR 'O'");
        Game.instructionBoard();

        out = new PrintStream(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        scanner = new Scanner(System.in);
        out.println("");

        boolean hasWinner = false;
        boolean isFull = false;

        while (!hasWinner && !isFull) {
            String messageFromClient = in.readLine();
            System.out.println();
            System.out.println("OPPONENT MOVED AT: " + messageFromClient);

            Game.placePiece(Integer.parseInt(messageFromClient), Game.CROSS);
            Game.displayBoard();

            if (Game.isFull() || Game.hasWinner()) {
                break;
            }

            boolean isValidMove = false;

            System.out.println("YOUR TURN!");

            String messageToClient = scanner.nextLine();

            if (Game.placePiece(Integer.parseInt(messageToClient), Game.CIRCLE)) {
                    out.println(messageToClient);
            } else {
                while (!isValidMove) {
                    System.out.println("INVALID MOVE. PLEASE, CHOOSE ANOTHER POSITION!");
                    messageToClient = scanner.nextLine();

                    if (Game.placePiece(Integer.parseInt(messageToClient), Game.CIRCLE)) {
                        isValidMove = true;
                        out.println(messageToClient);
                    }
                }
            }

            Game.displayBoard();

            hasWinner = Game.hasWinner();
            isFull = Game.isFull();

            if (!hasWinner && !isFull) {
                System.out.println("WAITING FOR OPPONENT'S MOVE...");
            }

        }

        if (Game.checkWinner(3)) {
            System.out.println("'X' WON! YOU LOST :(");
        } else {
            if (Game.checkWinner(-3)) {
                System.out.println("YOU WON! 'X' LOST :(");
            } else {
                System.out.println("DRAW");
            }
        }

        destroy();
    }

    private void destroy() throws IOException {
        in.close();
        out.close();
        scanner.close();
        serverSocket.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}
