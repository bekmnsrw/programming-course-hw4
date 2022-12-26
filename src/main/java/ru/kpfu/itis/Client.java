package ru.kpfu.itis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintStream out;
    private BufferedReader in;
    private Scanner scanner;

    public void run() throws IOException {
        clientSocket = new Socket(InetAddress.getLocalHost(), 9999);

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintStream(clientSocket.getOutputStream(), true);
        scanner = new Scanner(System.in);

        System.out.println("YOU'RE PLAYING FOR 'X'");
        Game.instructionBoard();
        System.out.println("YOUR TURN!");

        boolean hasWinner = false;
        boolean isFull = false;

        while (!hasWinner && !isFull) {
            String messageFromServer = in.readLine();

            if (!messageFromServer.equals("")) {
                System.out.println();
                System.out.println("OPPONENT MOVED AT: " + messageFromServer);

                Game.placePiece(Integer.parseInt(messageFromServer), Game.CIRCLE);
                Game.displayBoard();

                if (Game.isFull() || Game.hasWinner()) {
                    break;
                }

                System.out.println("YOUR TURN!");
            }

            boolean isValidMove = false;

            String messageToServer = scanner.nextLine();

            if (Game.placePiece(Integer.parseInt(messageToServer), Game.CROSS)) {
                out.println(messageToServer);
            } else {
                while (!isValidMove) {
                    System.out.println("INVALID MOVE. PLEASE, CHOOSE ANOTHER POSITION!");
                    messageToServer = scanner.nextLine();

                    if (Game.placePiece(Integer.parseInt(messageToServer), Game.CROSS)) {
                        isValidMove = true;
                        out.println(messageToServer);
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
            System.out.println("YOU WON! 'O' LOST :(");
        } else {
            if (Game.checkWinner(-3)) {
                System.out.println("'X' WON! YOU LOST :(");
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
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }
}
