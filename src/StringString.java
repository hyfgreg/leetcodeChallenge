import java.util.Arrays;
import java.util.HashMap;

class Student {
    private int key;
    private String name;

    Student(int key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "key=" + key +
                ", name='" + name + '\'' +
                '}';
    }

    public int key() {
        return key;
    }

    public String name() {
        return name;
    }
}

class Card {
    private String suit;
    private int number;
    private static HashMap<String, Integer> suitMap;

    static {
        suitMap = new HashMap<>();
        String[] suits = {"Spade", "Heart", "Diamond", "Club"};
        for (int i = 0; i < suits.length; i++) {
            suitMap.put(suits[i], i + 1);
        }
    }

    Card(String suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", number=" + number +
                '}';
    }

    public String getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    public int getSuitIndex() {
        return suitMap.get(suit);
    }
}

class IndexKeyCount {
    public static void main(String[] args) {
        String[] names = {"Anderson", "Brown", "Davis", "Garcia", "Harris", "Jackson", "Johnson", "Jones", "Martin", "Martinez", "Miller", "Moore", "Robinson", "Smith", "Taylor", "Thomas", "Thompson", "White", "Williams", "Wilson"};
        int[] keys = {2, 3, 3, 4, 1, 3, 4, 3, 1, 2, 2, 1, 2, 4, 3, 4, 4, 2, 3, 4};
        Student[] students = new Student[keys.length];
        for (int i = 0; i < keys.length; i++) {
            Student s = new Student(keys[i], names[i]);
            students[i] = s;
            StdOut.println(s);
        }
        int R = -1;
        for (int key : keys) {
            if (key > R) {
                R = key;
            }
        }
        R++;
        StdOut.println("R " + R);
        IndexKeyCount ikc = new IndexKeyCount();
        ikc.sort(students, R);
        for (Student s : students) {
            StdOut.println(s);
        }

    }

    public void sort(Student[] a, int R) {
        int N = a.length;
        Student[] aux = new Student[N];
        int[] count = new int[R + 1];
        // 统计每个key的出现的数量，+1使得count[r+1]保存的是key为r的对象的数量
        for (int i = 0; i < N; i++) {
            count[a[i].key() + 1]++;
        }
        // 统计每个key在数组中的起始索引,上一个key的数量 加上 上一个key的索引
        for (int r = 0; r < R; r++) {
            count[r + 1] = count[r] + count[r + 1];
        }
        // key->查找其索引->放入对应的位置,并++更新下一个该key的对象的索引
        for (int i = 0; i < N; i++) {
            aux[count[a[i].key()]++] = a[i];
        }
        // 从辅助数组回写到原数组
        for (int i = 0; i < N; i++) {
            a[i] = aux[i];
        }
    }
}

class LSD {
    public static void main(String[] args) {
        String[] suits = {"Spade", "Heart", "Diamond", "Club"};
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        Card[] cards = new Card[suits.length * numbers.length];
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                cards[i * numbers.length + j] = new Card(suits[i], numbers[j]);
            }
        }
        StdRandom.shuffle(cards, 0, 51);
        for (Card card : cards) {
            StdOut.println(card);
        }
        StdOut.println();
        LSD lsd = new LSD();
        lsd.arrangeCard(cards);
        for (Card card : cards) {
            StdOut.println(card);
        }
    }

    public void arrangeCard(Card[] a) {
        int N = a.length;
        Card[] aux = new Card[N];
        int R;


        // 按照花色
        R = 5; // 4 + 1
//        count = new int[R + 1];

        int[] count2 = new int[R + 1];
        for (int i = 0; i < N; i++) {
            count2[a[i].getSuitIndex() + 1]++;
        }
        for (int r = 0; r < R; r++) {
            count2[r + 1] += count2[r];
        }
        for (int b : count2) {
            StdOut.println(b);
        }
        for (int i = 0; i < N; i++) {
            StdOut.println(a[i] + " " + a[i].getSuitIndex());
            StdOut.println(count2[a[i].getSuitIndex()]);
            aux[count2[a[i].getSuitIndex()]++] = a[i];
        }
        for (int i = 0; i < N; i++) {
            a[i] = aux[i];
        }

        // 按照数字
        R = 14;
        int[] count = new int[R + 1];
        for (int i = 0; i < N; i++) {
            count[a[i].getNumber() + 1]++;
        }
        for (int r = 0; r < R - 1; r++) {
            count[r + 1] += count[r];
        }
        for (int i = 0; i < N; i++) {
//            StdOut.println(a[i]);
//            StdOut.println(count[a[i].getNumber()]);
            aux[count[a[i].getNumber()]++] = a[i];
        }
        for (int i = 0; i < N; i++) {
            a[i] = aux[i];
        }
    }
}


class MSD {
    private static int R = 256;
    private static final int M = 15;
    private static String[] aux;
    private static Insertion insert = new Insertion();

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {
            insert.sort(a, lo, hi, d);
            return;
        }
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }
        for (int r = 0; r < R; r++) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }
}

public class StringString {
}
