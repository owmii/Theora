package xieao.theora.core.lib.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayUtil {

    public static List<?> list(List<?>... lists) {
        return Stream.of(lists)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static Set<?> set(Set<?>... sets) {
        return Stream.of(sets)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
