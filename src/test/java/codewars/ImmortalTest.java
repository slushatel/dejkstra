package codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImmortalTest {

    @Test
    public void example() {
        {
            long startTime = System.nanoTime();

            assertEquals(5, Immortal.elderAge(8, 5, 1, 100));
            assertEquals(224, Immortal.elderAge(8, 8, 0, 100007));
            assertEquals(11925, Immortal.elderAge(25, 31, 0, 100007));
            assertEquals(4323, Immortal.elderAge(5, 45, 3, 1000007));
            assertEquals(1586, Immortal.elderAge(31, 39, 7, 2345));
            assertEquals(808451, Immortal.elderAge(545, 435, 342, 1000007));
            // You need to run this test very quickly before attempting the actual tests :)
            assertEquals(5456283, Immortal.elderAge(28827050410L, 35165045587L, 7109602, 13719506));
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println(totalTime);
        }

        {
            long startTime = System.nanoTime();

            assertEquals(5, ImmortalNotMine.elderAge(8, 5, 1, 100));
            assertEquals(224, ImmortalNotMine.elderAge(8, 8, 0, 100007));
            assertEquals(11925, ImmortalNotMine.elderAge(25, 31, 0, 100007));
            assertEquals(4323, ImmortalNotMine.elderAge(5, 45, 3, 1000007));
            assertEquals(1586, ImmortalNotMine.elderAge(31, 39, 7, 2345));
            assertEquals(808451, ImmortalNotMine.elderAge(545, 435, 342, 1000007));
            // You need to run this test very quickly before attempting the actual tests :)
            assertEquals(5456283, ImmortalNotMine.elderAge(28827050410L, 35165045587L, 7109602, 13719506));
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println(totalTime);
        }
    }

    @Test
    public void sumXor() {
        for (int i = 1; i <= 64; i++) {
            for (int j = 1; j <= 64; j++) {
                for (int loss = 0; loss < 10; loss++) {
                    for (int modulos = 1; modulos < 1000; modulos += 100) {
//                        int modulos = 1;
                        System.out.println(i + ":" + j + ", loss=" + loss + ", mod=" + modulos);
                        assertEquals(calcXorSumDirectly(i, j, loss, modulos), new Immortal().sum(0, i, j, loss, modulos));
                    }
                }
            }
        }
    }

    int calcXorSumDirectly(int a, int b, int loss, int modulos) {
        int sum = 0;
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                int xor = i ^ j;
                sum += xor > loss ? xor - loss : 0;
            }
        }
        return sum % modulos;
    }

}