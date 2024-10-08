package engine.sheetimpl.range;

import engine.api.Coordinate;

import java.util.List;

public interface Range {
    void initRange();

    List<Coordinate> getCoordinates();

    Coordinate getStart();

    Coordinate getEnd();
}
