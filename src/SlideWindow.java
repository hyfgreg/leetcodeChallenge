import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SlideWindow {
    public static void main(String[] args) {
        String source = "ADOBECODEBBANC";
        String target = "ABC";
        String sub = findMinSubString(source, target);
        System.out.printf("sub %s", sub);
    }

    public static String findMinSubString(String source, String target) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : target.toCharArray()) need.put(c, 1);
        int left = 0, right = 0;
        String ans = "";
        while (right < source.length()) {
            char c = source.charAt(right);
            if (need.containsKey(c)) {
                System.out.println("meed need, window: "+window.toString());
                int v = window.getOrDefault(c, 0);
                window.put(c, v + 1);
            }
            if (need.keySet().equals(window.keySet())) {
                // todo 缩小窗口
                System.out.println("need: " + need.toString());
                System.out.println("current window: " + window.toString());
                while (left <= right) {
                    char d = source.charAt(left);
                    if (need.containsKey(d)) {
                        if (window.get(d) == 1) {
                            String tmp = source.substring(left, right + 1);
                            System.out.printf("left: %d, right: %d, sub: %s\n", left, right, tmp);
                            if (ans.length() == 0 || tmp.length() < ans.length()) {
                                ans = tmp;
                            }
                            left = right + 1;
                            window.clear();
                            break;
                        }
                        window.put(d, window.get(d) - 1);
                    }
                    left++;
                }
            }
            right++;

        }
        return ans;
    }
}
