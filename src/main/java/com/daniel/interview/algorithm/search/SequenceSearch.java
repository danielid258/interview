package com.daniel.interview.algorithm.search;

/**
 * Daniel on 2018/11/13.
 * <p>
 * 顺序查找  时间复杂度为O(n)
 */
public class SequenceSearch {
    private int min = 0;
    private int max = 0;

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 0, 7, 5678, 80};
        SequenceSearch search = search(arr);
        System.out.println(search.getMax());
        System.out.println(search.getMin());
    }
    public static SequenceSearch search(int[] arr) {
        int min = 0;
        int max = 0;
        for (int i : arr) {
            if (i > max)
                max = i;
            if (i < min)
                min = i;
        }
        SequenceSearch search = new SequenceSearch();
        search.setMax(max);
        search.setMin(min);
        return search;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
