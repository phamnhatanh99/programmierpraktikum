package de.tukl.programmierpraktikum2021.mp1;

public class BibleAnalyzer {
    public static void countWords(Map<String, Integer> counts) {
        for (String word : Util.getBibleWords()) {
            if(counts.get(word) == null)
                counts.put(word, 1);
            else
                counts.put(word, counts.get(word) + 1);
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new ListMap<>();
        countWords(map);
        String[] words = new String[map.size()];
        map.keys(words);
        // Sort words TODO
        for (String word : words) {
            System.out.println(map.get(word) + " " + word);
        }
    }

    public static void sort(String[] words, Map<String, Integer> counts) {
        throw new RuntimeException("TODO");
    }
}
