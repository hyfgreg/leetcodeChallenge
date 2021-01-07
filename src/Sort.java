import javax.swing.plaf.SplitPaneUI;
import java.util.Arrays;

class SortBase {
    public void sort(Comparable[] a) throws NoSuchMethodException {
    }

    protected boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    protected void exch(Comparable[] a, int i, int j) {
        Comparable t = a[j];
        a[j] = a[i];
        a[i] = t;
    }

    protected void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    protected boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }
}

class Selection extends SortBase {
    /*
    运行时间和输入无关 约N^2/2次比较和N次变换
    数据移动是最少的
    * */
    public void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) min = j;
            }
            exch(a, i, min);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 4, 2, 5, 7, 3, 10};
        Selection s = new Selection();
        s.sort(a);
        s.show(a);

    }
}

class Insertion extends SortBase {
    /*
    就像整理扑克，拿出一张，在已经整理好的那里面找到这一张的合适位置，插进去
    平均需要~N^2/4次比较和~N^2/4交换，最坏需要~N^2/2次比较和~N^2/2次交换，最好需要N-1次比较和0次交换(就是个排好序的数组)
    对于部分排序的数组效率很高
    交换操作和数组中倒置的数量相同，需要的比较次数大于等于倒置的数量，小于等于倒置的数量加上数组的大小再减一
    * */
    public void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }
}


class Shell extends SortBase {
    public void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}

class Merge extends SortBase {
    private static Comparable[] aux;

    public void sort2(Comparable[] a) {
        // 自底向上，适合链表排序？重新组织链表链接就可以排序？
        //尾递归优化？
        aux = new Comparable[a.length];
        int N = a.length;
        for (int sz = 1; sz < N; sz = sz + sz) {
            // sz值得是left/right部分的大小
            for (int lo = 0; lo < N; lo += sz + sz) {
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
    }

    public void sort(Comparable[] a) {
        // 自顶向上
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            StdOut.println("return " + a[lo]);
            return;
        }
        ;
        int mid = lo + (hi - lo) / 2;

        StdOut.println("Sort left " + lo + " to " + mid);
//        StdOut.print("before ");
//        for(int i=lo;i<=mid;i++){
//            StdOut.print(a[i]+" ");
//        }
//        StdOut.print("\n");
        sort(a, lo, mid);
//        StdOut.print("after ");
//        for(int i=lo;i<=mid;i++){
//            StdOut.print(a[i]+" ");
//        }
//        StdOut.print("\n");

        StdOut.println("Sort right " + (mid + 1) + " to " + hi);
//        StdOut.print("before ");
//        for(int i=mid+1;i<=hi;i++){
//            StdOut.print(a[i]+" ");
//        }
//        StdOut.print("\n");
        sort(a, mid + 1, hi);
//        StdOut.print("after ");
//        for(int i=mid+1;i<=hi;i++){
//            StdOut.print(a[i]+" ");
//        }
//        StdOut.print("\n");

        StdOut.println("merge " + lo + " " + mid + " " + hi);
        merge(a, lo, mid, hi);
    }

    private void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1; // i left index, j right index
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        Merge m = new Merge();
        m.sort(a);
        for (String i : a) {
            StdOut.printf(i + ", ");
        }
    }
}

class Quick extends SortBase {

    public void sort(Comparable[] a) {
//        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        StdOut.println(a[j] + " index " + j);
        for (Comparable i : a) {
            StdOut.print(i + " ");
        }
        StdOut.println();
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private int partition(Comparable[] a, int lo, int hi) {
        int i = lo + 1, j = hi;
        Comparable v = a[lo];
        // 左侧的值作为锚点，交换左边(小)的
        // 右侧的值作为锚点，交换右边(大)的
        while (true) {
//            StdOut.println("i " + i + " j " + j);
            while (less(a[i], v) && i < hi) {
                i++;
            }

            while (less(v, a[j]) && j > lo) {
                j--;
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
            i++;
            j--;
        }
        StdOut.println("lo " + lo + " hi " + hi + " i " + i+" j "+j);
        for (Comparable abc : a) {
            StdOut.print(abc + " ");
        }
        StdOut.println();
        exch(a, lo, j);
        return j;
    }

    public static void main(String[] args) {
        String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        Quick m = new Quick();
        m.sort(a);
        for (String i : a) {
            StdOut.printf(i + ", ");
        }
    }
}

public class Sort {

}
