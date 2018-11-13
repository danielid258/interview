package com.daniel.interview.algorithm.search;

/**
 * Daniel on 2018/11/13.
 * <p>
 * 二分查找 也称折半查找 时间复杂度为O(log2n)
 * 前提条件是需要待查找的数组有序
 * <p>
 * 基本思想
 * 用给定值k先与中间结点的关键字比较,中间结点把线形表分成两个子表,若相等则查找成功；
 * 若不相等,再根据k与该中间结点关键字的比较结果确定下一步查找哪个子表,这样递归进行,直到查找到或查找结束发现表中没有这样的结点。
 * <p>
 * 　　注：折半查找的前提条件是需要有序表顺序存储,对于静态查找表,一次排序后不再变化,折半查找能得到不错的效率。但对于需要频繁执行插入或删除操作的数据集来说,维护有序的排序会带来不小的工作量,那就不建议使用。
 */
public class BinarySearch {
    static int search(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;

        // 这里必须是 <=
        //否则判断条件不完整,比如：array[3] = {1, 3, 5};待查找的键为5,此时在(low < high)条件下就会找不到
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    static int findFirstEqual(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;

        // 这里必须是 <=
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (left < array.length && array[left] == key) {
            return left;
        }

        return -1;
    }
}
