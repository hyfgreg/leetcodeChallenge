import java.lang.reflect.Array;
import java.util.*;

public class Coins {
    public static void main(String[] args) {
        int[] coins = {2, 3, 5};
        int amount = 11;
//        Map<Integer, Integer> memo = new HashMap<>();
//        double res = dp(amount, coins, memo);
//
//        System.out.printf("result for %d with %s is %d, memo: %s", amount, Arrays.toString(coins), (int) res, memo.toString());
        ArrayList<Double> res = iterateDP(amount, coins);
        ArrayList<Integer> tmp = new ArrayList<>();
        for (double d : res) {
            tmp.add((int) d);
        }
        System.out.printf("result for %d with %s is %s", amount, Arrays.toString(coins), tmp.toString());
    }

    public static double dp(int amount, int[] coins, Map<Integer, Integer> memo) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        if (memo.containsKey(amount)) return (double) memo.get(amount);
        double res = Double.POSITIVE_INFINITY;
        for (int coin : coins) {
            double sub = dp(amount - coin, coins, memo);
            if (sub < 0) continue;
            res = Math.min(res, 1 + sub);
        }
        memo.put(amount, (int) res);
        return res;
    }

    public static ArrayList<Double> iterateDP(int amount, int[] coins) {
        ArrayList<Double> res = new ArrayList<>();
        int i = 0;
        while (i <= amount) {
            res.add(Double.POSITIVE_INFINITY);
            for (int coin : coins) {
                if(coin == i){
                    res.set(i, (double) 1);
                    continue;
                }
                int index = i - coin;
                if (index < 0) continue;
                if(res.get(index) < 1) continue;
                res.set(i, Math.min(res.get(index) + 1, res.get(i)));
            }
            if (res.get(i).equals(Double.POSITIVE_INFINITY)) {
                res.set(i, (double) 0);
            }
            i++;
        }
        return res;
    }
}
