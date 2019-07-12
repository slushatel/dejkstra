import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PermutationsSwap {
    static int dataSize;

    public static void main(String[] args) {
        final int[] dataInitial = {1, 2, 3, 4};
        dataSize = dataInitial.length;
        new PermutationsSwap().getPermutations(dataInitial, 0).stream().map(Arrays::toString).forEach(System.out::println);
    }

    List<int[]> getPermutations(int[] data, int index) {
        List<int[]> res = new LinkedList<>();
        if (index == dataSize) return res;
        res.add(data);

        for (int i = index; i < dataSize; i++) {
            int[] dataSwapped = swap(data, i, index);
            getPermutations(dataSwapped, index + 1);
        }
        getPermutations(data, index + 1);

        return res;
    }

    int[] swap(int[] data, int a, int b) {
        int[] res = Arrays.copyOf(data, dataSize);
        int tmp = res[a];
        res[a] = res[b];
        res[b] = tmp;
        return res;
    }
}
