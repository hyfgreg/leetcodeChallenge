import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Queen {
    public static void main(String[] args) {
        int n = 5;
        ArrayList<String[][]> res = queenSolve(n);
        for (String[][] board : res) {
            for(String[] row: board){
                System.out.println(Arrays.toString(row));
            }
            System.out.println("===========================");
        }
        System.out.printf("%d皇后的答案个数: %d", n, res.size());
    }

    public static ArrayList<String[][]> queenSolve(int n) {
        String[][] board = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = ".";
            }
        }

        ArrayList<String[][]> res = new ArrayList<String[][]>();

        backtrack(res, board, 0);
        return res;
    }

    public static void backtrack(ArrayList<String[][]> res, String[][] board, int row) {
        if (row == board.length) {
            String[][] copyBoard = new String[board.length][];
            for (int i = 0; i < board.length; i++) {
                copyBoard[i] = new String[board[i].length];
                System.arraycopy(board[i], 0, copyBoard[i], 0, board[i].length);
            }
            res.add(copyBoard);
            return;
        }

        int n = board[row].length;
        for (int col = 0; col < n; col++) {
            if (!isValid(board, row, col)) continue;
            board[row][col] = "Q";
            backtrack(res, board, row + 1);
            board[row][col] = ".";
        }
    }

    public static boolean isValid(String[][] board, int row, int col) {
        int n = board.length;
        for (String[] strings : board) {
            if (strings[col].equals("Q")) return false;
        }
        // 只看左上角和右上角即可
        // 左上角
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j].equals("Q")) return false;
        }

        // 右上角
        for (int i = row, j = col; i >= 0 && j < n; i--, j++) {
            if (board[i][j].equals("Q")) return false;
        }
        return true;
    }

}
