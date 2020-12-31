import java.util.*;

public class PermutationInString {
    public static void main(String[] args) {
//        String target = "ab";
//        String source = "eidbboaooo";
//        System.out.println(solute(source, target));
//        String source = "aaaaaaaa";
//        String target = "aa";
//        System.out.println(generalSoluteAnagrams(source, target).toString());
        String source = "pwwkew";
        System.out.println(longestSubString(source));

    }

    public static int longestSubString(String source) {
        HashMap<Character, Integer> window = new HashMap<>();
        int res = 0;
        int tmp = 0;
        int left = 0, right = 0;
        while (right < source.length()) {
            char c = source.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);
            while(window.get(c) > 1){
                // 这个循环里面将找到上一个c的右边的字符的索引，且这个索引<=right - 1
                char d = source.charAt(left);
                left ++;
                window.put(d, window.get(d) - 1);
            }
            res = Math.max(res, right - left);
//            if(!window.contains(c)){
//                tmp ++;
//                window.add(c);
//            }else{
//                if(tmp > res) res = tmp;
//                while(left<=right){
//                    // 找到上一个c的右边
//                    char d = source.charAt(left);
//                    left ++;
//                    if(d == c){
//                        tmp = right - left;
//                        break;
//                    }
//                }
//            }

        }
        return res;
    }

    public static ArrayList<Integer> generalSoluteAnagrams(String source, String target) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        ArrayList<Integer> ret = new ArrayList<>();
        for (char c : target.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);
        int left = 0, right = 0;
        int valid = 0;
        while (right < source.length()) {
            char c = source.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            while (right - left >= target.length()) {
                if (valid == need.size()) {
                    ret.add(left);
                }
                char d = source.charAt(left);
                left++;
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) == 0 ? 0 : window.get(d) - 1);
                }
            }
        }
        return ret;
    }


    public static boolean windowNeedShrink2(Map<Character, Integer> window, Map<Character, Integer> need) {
        if (!window.keySet().equals(need.keySet())) return false;
        for (char k : need.keySet()) {
            if (!window.get(k).equals(need.get(k))) return false;
        }
        return true;
    }

    public static boolean solute(String source, String target) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : target.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);
        int right = 0;
        while (right < source.length()) {
            char c = source.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
            } else {
                window.clear();
//                left = right;
                continue;
            }
            if (windowNeedShrink(window, need)) return true;
        }
        return false;
    }

    public static boolean windowNeedShrink(Map<Character, Integer> window, Map<Character, Integer> need) {
        if (!window.keySet().equals(need.keySet())) return false;
        for (char k : need.keySet()) {
            if (window.get(k) % need.get(k) != 0) return false;
        }
        return true;
    }

    public static ArrayList<Integer> anagrams(String source, String target) {
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char c : target.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);
        int left = 0, right = 0;
        ArrayList<Integer> ret = new ArrayList<>();
        while (right < source.length()) {
            char c = source.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
            } else {
                window.clear();
                left = right;
                continue;
            }
            System.out.printf("char %s, window: %s\n", c, window.toString());
            if (window.get(c) > need.get(c)) {
                System.out.printf("window[%s]==%d, > need[%s]==%d\n", c, window.get(c), c, need.get(c));
                window.clear();
                window.put(c, 1);
                left = right - 1;
                continue;
            }
            if (window.equals(need)) {
                System.out.println("left: " + left + ", right: " + right + " sub: " + source.substring(left, right));
                System.out.println("current window: " + window.toString());
                ret.add(left);
                left = right;
                window.clear();
            }
        }
        return ret;
    }

}
