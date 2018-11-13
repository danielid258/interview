package com.daniel.interview.manager;

import com.daniel.interview.service.TableSplitStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Daniel on 2018/11/5.
 */
@Slf4j
public class TableSplitStrategyManager {
    private ConcurrentMap<String, TableSplitStrategy> strategies = new ConcurrentHashMap(10);

    public TableSplitStrategy getStrategy(String key) {
        return strategies.get(key);
    }

    public ConcurrentMap<String, TableSplitStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(ConcurrentMap<String, TableSplitStrategy> strategies) {
        strategies.forEach((key, value) -> {
            try {
                this.strategies.put(key, (TableSplitStrategy) Class.forName(String.valueOf(value)).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
