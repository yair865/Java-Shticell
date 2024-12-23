package dto.dtoPackage.range;

import dto.dtoPackage.coordinate.Coordinate;
import dto.dtoPackage.coordinate.CoordinateFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RangeImpl implements Range , Serializable {
    private Coordinate start;
    private Coordinate end;
    private List<Coordinate> cellsInRange;

    public RangeImpl(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.initRange();
    }

    private void initRange() {
        cellsInRange = new ArrayList<>();

        int startRow = start.row();
        int endRow = end.row();
        int startCol = start.column();
        int endCol = end.column();

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                Coordinate coordinate = CoordinateFactory.createCoordinate(row, col);
                cellsInRange.add(coordinate);
            }
        }
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return cellsInRange;
    }

    @Override
    public Coordinate getStart() {
        return start;
    }

    @Override
    public Coordinate getEnd() {
        return end;
    }
}
