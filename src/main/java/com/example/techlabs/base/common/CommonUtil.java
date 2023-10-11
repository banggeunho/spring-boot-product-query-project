package com.example.techlabs.base.common;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
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

        if (list == null) {
            throw new IllegalArgumentException("Input list cannot be null.");
        }

        if (function == null) {
            throw new IllegalArgumentException("Key extraction function cannot be null.");
        }

        return list.stream()
                .filter(Objects::nonNull)
                .map(function)
                .collect(Collectors.toList());
    }

}
