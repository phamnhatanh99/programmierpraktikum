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
        sort(words, map);
        for (String word : words) {
            System.out.println(map.get(word) + " " + word);
        }
    }

    /**
     * Sorts an array using Heap Sort
     *
     * Reference
     * https://www.geeksforgeeks.org/heap-sort/
     **/
    public static void sort(String[] words, Map<String, Integer> counts) {
        int n = words.length - 1;

        // Create a Heap
        for (int i = n / 2; i >= 0; i--)
            heapify(words, counts, n, i);

        // Sort the largest element then heapify
        for (int i = n ; i > 0; i--) {
            swap(words, i, 0);
            heapify(words, counts, i, 0);
        }
    }

    /**
     * Transform a given array into a heap
     *
     * @param arr   the array to be heapified.
     * @param map   used as reference to compare elements in arr.
     * @param n     the last index of arr.
     * @param root  the current root of the heap.
     */
    private static void heapify(String[] arr, Map<String, Integer> map, int n, int root) {
        int tmpRoot = root; // used to determine which node to swap to
        int l = 2 * root + 1; // Left node
        int r = 2 * root + 2; // Right node

        // Check left node and set it as tmp root if its larger
        if (l < n && map.get(arr[l]) > map.get(arr[tmpRoot]))
            tmpRoot = l;

        // Check right node and set it as tmp root if its larger
        if (r < n && map.get(arr[r]) > map.get(arr[tmpRoot]))
            tmpRoot = r;

        // If one of the node is larger than root swap and heapify sub trees
        if (tmpRoot != root) {
            swap(arr, root, tmpRoot);
            heapify(arr, map, n, tmpRoot);
        }
    }

    // Swap two elements in an array
    private static void swap(String[] arr, int a, int b) {
        String tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
