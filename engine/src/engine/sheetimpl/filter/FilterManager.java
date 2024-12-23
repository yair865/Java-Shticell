package engine.sheetimpl.filter;

import dto.dtoPackage.coordinate.Coordinate;
import engine.sheetimpl.SpreadsheetImpl;

import java.util.List;

public interface FilterManager {

    void filterSheet(Character selectedColumn, List<Coordinate> rangesToFilter, List<String> selectedValues, SpreadsheetImpl spreadsheet);
}
