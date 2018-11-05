package com.daniel.interview.service.impl;

import com.daniel.interview.service.IndexService;
import org.springframework.stereotype.Service;

/**
 * Daniel on 2018/11/5.
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Override
    public void test() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("========== IndexServiceImpl: test");
    }
}
