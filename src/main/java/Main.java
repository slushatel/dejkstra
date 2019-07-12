import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Main {

    private final List<List<Integer>> data = getData();
    private final int numRows = data.size();
    private final int numCols = data.get(0).size();
    private final Cell targetCell = getTarget(data);
    private final static int TARGET_VALUE = 9;

    private int[][] distances = new int[numRows][numCols];
    private List<Cell> unvisited = new LinkedList<>();

    {
        initDist();
        initUnvisited();
    }

    Cell getTarget(List<List<Integer>> data) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (data.get(i).get(j) == TARGET_VALUE) return new Cell(i, j);
            }
        }
        throw new RuntimeException("Target not found on the map");
    }

    class Cell {
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x &&
                    y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Main().findPath());
    }

    List<List<Integer>> getData() {
        return asList(
                asList(1, 1, 1, 0),
                asList(1, 1, 0, 1),
                asList(1, 1, 0, 9),
                asList(1, 1, 1, 1));
    }

    void initDist() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).size(); j++) {
                distances[i][j] = Integer.MAX_VALUE;
            }
        }
        distances[0][0] = 0;
    }

    void initUnvisited() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).size(); j++) {
                unvisited.add(new Cell(i, j));
            }
        }
    }

    private int findPath() {
        Cell current = new Cell(0, 0);
        while (unvisited.size() > 0) {
            int minDist = Integer.MAX_VALUE;
            for (Cell c : unvisited) {
                if (distances[c.x][c.y] <= minDist) {
                    minDist = distances[c.x][c.y];
                    current = c;
                }
            }

            List<Cell> possibleNeighbours = asList(
                    new Cell(current.x - 1, current.y), new Cell(current.x + 1, current.y), new Cell(current.x, current.y - 1), new Cell(current.x, current.y + 1));
            List<Cell> unvisitedNeighbours = unvisited.stream()
                    .distinct()
                    .filter(possibleNeighbours::contains)
                    .collect(Collectors.toList());
            for (Cell neibourgh : unvisitedNeighbours) {
                if (data.get(neibourgh.x).get(neibourgh.y) != 0) {
                    distances[neibourgh.x][neibourgh.y] = Math.min(distances[neibourgh.x][neibourgh.y], distances[current.x][current.y] + 1);
                }
            }
            unvisited.remove(current);
        }
        return distances[targetCell.x][targetCell.y];
    }

}
