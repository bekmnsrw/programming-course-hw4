package ru.kpfu.itis;

public class Game {
    public static final String CROSS = "X";
    public static final String CIRCLE = "O";
    private static int[][] board = new int[3][3];

    public static boolean checkWinner(int pieceSum) {
        return board[0][0] + board[0][1] + board[0][2] == pieceSum ||
                board[1][0] + board[1][1] + board[1][2] == pieceSum ||
                board[2][0] + board[2][1] + board[2][2] == pieceSum ||
                board[0][0] + board[1][0] + board[2][0] == pieceSum ||
                board[0][1] + board[1][1] + board[2][1] == pieceSum ||
                board[0][2] + board[1][2] + board[2][2] == pieceSum ||
                board[0][0] + board[1][1] + board[2][2] == pieceSum ||
                board[0][2] + board[1][1] + board[2][0] == pieceSum;
    }

    public static boolean hasWinner() {
        return board[0][0] + board[0][1] + board[0][2] == 3 ||
                board[1][0] + board[1][1] + board[1][2] == 3 ||
                board[2][0] + board[2][1] + board[2][2] == 3 ||
                board[0][0] + board[1][0] + board[2][0] == 3 ||
                board[0][1] + board[1][1] + board[2][1] == 3 ||
                board[0][2] + board[1][2] + board[2][2] == 3 ||
                board[0][0] + board[1][1] + board[2][2] == 3 ||
                board[0][2] + board[1][1] + board[2][0] == 3 ||
                board[0][0] + board[0][1] + board[0][2] == -3 ||
                board[1][0] + board[1][1] + board[1][2] == -3 ||
                board[2][0] + board[2][1] + board[2][2] == -3 ||
                board[0][0] + board[1][0] + board[2][0] == -3 ||
                board[0][1] + board[1][1] + board[2][1] == -3 ||
                board[0][2] + board[1][2] + board[2][2] == -3 ||
                board[0][0] + board[1][1] + board[2][2] == -3 ||
                board[0][2] + board[1][1] + board[2][0] == -3;
    }

    public static void instructionBoard() {
        System.out.println();
        System.out.println("INSTRUCTION BOARD:");
        System.out.println("| 1 | 2 | 3 |");
        System.out.println("| - | - | - |");
        System.out.println("| 4 | 5 | 6 |");
        System.out.println("| - | - | - |");
        System.out.println("| 7 | 8 | 9 |");
        System.out.println();
    }

    public static void displayBoard() {
        System.out.println(printBoardRow(0));
        System.out.println("| - | - | - |");
        System.out.println(printBoardRow(1));
        System.out.println("| - | - | - |");
        System.out.println(printBoardRow(2));
        System.out.println();
    }

    private static String printBoardRow(int row) {
        StringBuilder rowBuilder = new StringBuilder("| ");

        for (int i = 0; i < board[0].length; i++) {
            if (board[row][i] == 0) rowBuilder.append(" ");
            if (board[row][i] == 1) rowBuilder.append("X");
            if (board[row][i] == -1) rowBuilder.append("O");
            rowBuilder.append(" | ");
        }

        rowBuilder.deleteCharAt(rowBuilder.lastIndexOf(" "));

        return rowBuilder.toString();
    }

    public static boolean placePiece(int position, String pieceType) {
        int row = (position - 1) / 3;
        int col = (position - (row * 3)) - 1;

        if (board[row][col] == 0) {
            if (pieceType.equals("X")) board[row][col] = 1;
            if (pieceType.equals("O")) board[row][col] = -1;
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFull(){
        for (int row = 0; row < board.length; row++){
            for (int col = 0 ;col < board[0].length; col++){
                if (board[row][col] == 0) return false;
            }
        }
        
        return true;
    }
}
