package com.example.techlabs.base.common;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommonUtil {

    public static  <T> T findByKey(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public static <T, R> Map<T, R> groupByKey(List<R> list, Function<R, T> function) {
        return list.stream()
                .collect(Collectors.toMap(function, Function.identity()));
    }

    public static <T, R> List<R> extractKey(List<T> list, Function<T, R> function) {
        return list.stream()
                .map(function)
                .collect(Collectors.toList());
    }

}
