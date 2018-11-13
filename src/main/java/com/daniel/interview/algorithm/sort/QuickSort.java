package com.daniel.interview.algorithm.sort;

import java.util.Arrays;

/**
 * Daniel on 2018/11/13.
 * 快速排序为什么快 分治
 * 排序方法都是基于两两比较的 关键在于减少比较的次数
 * 例子 {1,2,3,4,5,15,78,89,90,100,200}
 * 思想，就是从中抽取一个基准数 然后大于基准的在一边，小于或等于在另一边。
 * 比如，现在抽取78，那么1，2,3,4,5,15会在一边，89,90,100，200会在另一边。从这时开始，小于78的数就不再与大于78的数进行比较了
 * 快速排序用了分而治之的思想 把大问题分成了两个小问题 然后递归下去
 */
public class QuickSort {
    private int[] arr = {1, 2, 3, 4, 5, 15, 78, 89, 90, 100, 200};


    public static void sort(int[] arr, int startIndex, int endIndex) {
        //递归结束条件:startIndex >= endIndex
        if (startIndex >= endIndex)
            return;
        //得到基准元素位置
        int pivotIndex = partition(arr, startIndex, endIndex);
        //分治法 递归数列的两部分
        sort(arr, startIndex, pivotIndex - 1);
        sort(arr, pivotIndex + 1, endIndex);
    }

    private static int partition(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
// 坑的位置，初始等于pivot的位置
        int index = startIndex;
//大循环在左右指针重合或者交错时结束
        while (right >= left) {
//right指针从右向左进行比较
            while (right >= left) {
                if (arr[right] < pivot) {
                    arr[left] = arr[right];
                    index = right;
                    left++;
                    break;
                }
                right--;
            }
//left指针从左向右进行比较
            while (right >= left) {
                if (arr[left] > pivot) {
                    arr[right] = arr[left];
                    index = left;
                    right--;
                    break;
                }
                left++;
            }
        }
        arr[index] = pivot;
        System.out.println(Arrays.toString(arr));
        return index;
    }
}