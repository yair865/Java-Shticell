package dto.dtoPackage;

import engine.api.Coordinate;
import engine.sheetimpl.range.Range;

import java.util.List;
import java.util.Map;

public record SpreadsheetDTO(String name, int version, Map<Coordinate , CellDTO> cells
        , int rows , int columns , int rowHeightUnits , int columnWidthUnits , Map<Coordinate,
        List<Coordinate>> dependenciesAdjacencyList, Map<Coordinate, List<Coordinate>> referencesAdjacencyList,
                             List<Coordinate> cellsThatHaveChanged, Map<String, Range> ranges) {

    public int numberOfModifiedCells() {
        return cellsThatHaveChanged.size();
    }
}