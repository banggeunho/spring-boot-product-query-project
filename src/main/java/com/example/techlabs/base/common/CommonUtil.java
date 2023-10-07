package com.example.techlabs.base.common;

import java.util.List;
import java.util.function.Predicate;

public class CommonUtil {

    public static  <T> T findByKey(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
