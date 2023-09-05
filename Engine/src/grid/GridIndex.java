package grid;

import java.util.Objects;

public class GridIndex {
    private int row;
    private int col;

    public GridIndex(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridIndex index = (GridIndex) o;
        return row == index.row && col == index.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
