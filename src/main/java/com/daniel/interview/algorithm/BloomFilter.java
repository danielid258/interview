package com.daniel.interview.algorithm;

/**
 * Daniel on 2018/11/30.
 * 布隆过滤
 */
public class BloomFilter {
    private int arraySize;
    private int[] array;

    public BloomFilter(int arraySize) {
        this.arraySize = arraySize;
        array = new int[arraySize];
    }

    public static void main(String[] args) {

    }
}