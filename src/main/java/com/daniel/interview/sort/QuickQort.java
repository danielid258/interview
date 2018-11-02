package com.daniel.interview.sort;

/**
 * Daniel on 2018/10/25.
 */
public class QuickQort {
    public static void main(String[] args) {

    }

    private static void sort(int[] a,int left,int right) {
        if(left>right)
            return;
        int pivot=a[left];//定义基准值为数组第一个数
        int i=left;
        int j=right;

        while(i<j)
        {
            while(pivot<=a[j]&&i<j)//从右往左找比基准值小的数
                j--;
            while(pivot>=a[i]&&i<j)//从左往右找比基准值大的数
                i++;
            if(i<j)                     //如果i<j，交换它们
            {
                int temp=a[i];
                a[i]=a[j];
                a[j]=temp;
            }
        }
        a[left]=a[i];
        a[i]=pivot;//把基准值放到合适的位置
        sort(a,left,i-1);//对左边的子数组进行快速排序
        sort(a,i+1,right);//对右边的子数组进行快速排序
    }
}
