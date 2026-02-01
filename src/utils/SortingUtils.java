package utils;

import java.util.List;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortingUtils {

    public static <T, U extends Comparable<U>> void sortByField(List<T> items,
                                                                Function<T, U> extractor,
                                                                boolean ascending) {
        if (ascending) {
            items.sort(Comparator.comparing(extractor));
        } else {
            items.sort(Comparator.comparing(extractor).reversed());
        }
    }

    public static <T> void sortByPrice(List<T> items, Function<T, Double> priceExtractor) {
        sortByField(items, priceExtractor, true);  // true = ascending
    }
    public static <T> List<T> filter(List<T> items, Predicate<T> predicate) {
        return items.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> T findFirst(List<T> items, Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst().orElse(null);
    }

    public static <T> long count(List<T> items, Predicate<T> predicate) {
        return items.stream().filter(predicate).count();
    }
}
