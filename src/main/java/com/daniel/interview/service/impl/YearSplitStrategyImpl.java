package com.daniel.interview.service.impl;

import com.daniel.interview.service.TableSplitStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Daniel on 2018/11/5.
 */
public class YearSplitStrategyImpl implements TableSplitStrategy {
    @Override
    public String convert(String tableName) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        StringBuilder sb = new StringBuilder(tableName);
        sb.append("_");
        sb.append(sdf.format(new Date()));
        return sb.toString();
    }
}
