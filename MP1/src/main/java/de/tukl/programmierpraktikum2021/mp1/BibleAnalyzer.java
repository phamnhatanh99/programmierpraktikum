package de.tukl.programmierpraktikum2021.mp1;

public class BibleAnalyzer {
    public static void countWords(Map<String, Integer> counts) {
        int counter = 0;
        for (String fixed : Util.getBibleWords()) {
            counter = 0;
            if(counts.get(fixed) == null){
                for (String word : Util.getBibleWords()) {
                    if(word.equals(fixed)) counter++;
                }

                counts.put(fixed, counter);
            }
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
