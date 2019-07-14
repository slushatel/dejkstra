import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PermutationsSwap {
    static int dataSize;

    public static void main(String[] args) {
        final int[] dataInitial = {0, 1, 2, 3};
        dataSize = dataInitial.length;
        new PermutationsSwap().getPermutations(dataInitial, 0);
    }

    void getPermutations(int[] data, int index) {
        if (index == dataSize - 1) {
            return;
        }
        if (index == 0) {
            System.out.println(Arrays.toString(data));
        }

        getPermutations(data, index + 1);

        for (int i = index + 1; i < dataSize; i++) {
            swap(data, index, i);
            System.out.println(Arrays.toString(data));
            getPermutations(data, index + 1);
            swap(data, i, index);
        }
    }

    void swap(int[] data, int a, int b) {
        int tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }
}
