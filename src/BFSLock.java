import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class SwingLock {
    public String sl;

    public SwingLock(String s) {
        sl = s;
    }

    public SwingLock plusOne(int index) {
        char[] slArray = sl.toCharArray();
        if (slArray[index] == '9') {
            slArray[index] = '0';

        } else {
            slArray[index]++;
        }
        SwingLock slP = new SwingLock(new String(slArray));
        return slP;
    }

    public SwingLock minusOne(int index) {
        char[] slArray = sl.toCharArray();
        if (slArray[index] == '0') {
            slArray[index] = '9';

        } else {
            slArray[index]--;
        }
        SwingLock slP = new SwingLock(new String(slArray));
        return slP;
    }
}

public class BFSLock {
    public static void main(String[] args) {
        String[] deadEnds = {"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"};
        String target = "8888";
        int minT = minTurn(deadEnds, target);
        System.out.printf("%d", minT);
    }

    public static int minTurn(String[] deadEnds, String target) {
        Set<String> visited = new HashSet<>();
        Set<String> deads = new HashSet<>();
        for (String dead : deadEnds) deads.add(dead);
        String start = "0000";
        Queue<SwingLock> q = new LinkedList<>();
        int turn = 0;
        SwingLock sl = new SwingLock(start);
        q.offer(sl);
        while (q.size() > 0) {
            System.out.printf("++++++++++++++++++++++++++++++++++++++++++\n");
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                System.out.printf("===================================\n");
                SwingLock l = q.poll();
                System.out.printf("Current lock %s\n", l.sl);
                if (l.sl.equals(target)) {
                    return turn;
                }
//                visited.add(l.sl);
                if (deads.contains(l.sl)) {
                    System.out.printf("Current lock is deadEnd %s, continue\n", l.sl);
                    continue;
                }
                for (int j = 0; j < target.length(); j++) {
                    SwingLock slPlus = l.plusOne(j);
                    SwingLock slMinus = l.minusOne(j);
                    if (visited.contains(slPlus.sl)) {
                        System.out.printf("Current lock has been visited %s, continue\n", slPlus.sl);
                    } else {
                        q.offer(slPlus);
                        System.out.printf("Put new lock %s\n", slPlus.sl);
                        visited.add(slPlus.sl);
                    }
                    if (visited.contains(slMinus.sl)) {
                        System.out.printf("Current lock has been visited %s, continue\n", slMinus.sl);
                    } else {
                        q.offer(slMinus);
                        System.out.printf("Put new lock %s\n", slMinus.sl);
                        visited.add(slMinus.sl);
                    }
                }
            }
            turn++;
            System.out.printf("turn %d\n", turn);
        }
        return -1;
    }
}
