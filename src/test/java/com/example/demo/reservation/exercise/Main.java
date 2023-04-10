import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<Integer, List<String>> input = new HashMap<>();
        input.put(1, Arrays.asList("salut", "dudu", "popa"));
        input.put(2, Arrays.asList("salut", "salut", "salut", "popa"));
        input.put(3, Arrays.asList("popa", "dudu", "dudu"));

        System.out.println(findMostCommonString(input));
    }

    public static String findMostCommonString(Map<Integer, List<String>> input) {
        Map<String, Integer> countMap = new HashMap<>();

        for (List<String> list : input.values()) {
            Set<String> uniqueStrings = new HashSet<>(list);
            for (String s : uniqueStrings) {
                countMap.put(s, countMap.getOrDefault(s, 0) + Collections.frequency(list, s));
            }
        }

        int maxCount = 0;
        String mostCommonString = null;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (appearsInAllLists(input, entry.getKey()) && entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonString = entry.getKey();
            }
        }

        return mostCommonString;
    }

    public static boolean appearsInAllLists(Map<Integer, List<String>> input, String target) {
        for (List<String> list : input.values()) {
            if (!list.contains(target)) {
                return false;
            }
        }
        return true;
    }
}
