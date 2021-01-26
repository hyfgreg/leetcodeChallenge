import java.util.Arrays;

class RemoveCoveredIntervals1288 {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

//        int left = intervals[0][0];
        int right = intervals[0][1];
        int res = 0;

        int sz = intervals.length;
        for (int i = 1; i < sz; i++) {
            if (intervals[i][1] <= right) {
                res++;
            } else if (right >= intervals[i][0]) {
                right = intervals[i][1];
            } else {
//                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        return sz - res;
    }
}

class MergeIntervals56 {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        int coveredIndex = 0;
        int right = intervals[0][1];
        int res = 0;

        int sz = intervals.length;
        for (int i = 1; i < sz; i++) {
            if (intervals[i][1] <= right) {
                continue;
            } else if (right >= intervals[i][0]) {
                right = intervals[i][1];
                intervals[coveredIndex][1] = right;
            } else {
                right = intervals[i][1];
                ++coveredIndex;
                intervals[coveredIndex][0] = intervals[i][0];
                intervals[coveredIndex][1] = intervals[i][1];
            }
        }
        return Arrays.copyOf(intervals, coveredIndex + 1);
    }
}


class IntervalListIntersections986 {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {

    }
}