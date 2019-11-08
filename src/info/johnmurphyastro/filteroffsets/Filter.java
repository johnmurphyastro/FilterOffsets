/*
 * Filter Offset is used to store filter focusOffset positions and flat exposure times
 * Copyright (C) 2019  John Murphy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package info.johnmurphyastro.filteroffsets;

import java.util.List;
import java.util.prefs.Preferences;

/**
 *
 * @author John Murphy
 */
public class Filter {
    private final int nthFilter;
    private String name;
    private int focusOffset;
    private double flatExposure;
    
    private static final String FILTER_NAME = "filter_name";
    private static final String FLAT_EXPOSURE = "flat_exposure";
    private static final String FOCUS_OFFSET = "filter_offset";
    private static final String FOCUS_POSITION = "focus_position";

    public Filter(int nthFilter, String name, double flatExposure, int focusOffset) {
        this.nthFilter = nthFilter;
        this.name = name;
        this.flatExposure = flatExposure;
        this.focusOffset = focusOffset;
    }
    
    public int getNthFilter() {
        return nthFilter;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getFocusOffset() {
        return focusOffset;
    }
    
    public static int getFocusPosition(int zeroOffsetPosition, int focusOffset){
        return zeroOffsetPosition + focusOffset;
    }

    public void setFocusOffset(int focus) {
        this.focusOffset = focus;
    }

    public double getFlatExposure() {
        return flatExposure;
    }

    public void setFlatExposure(int flatExposure) {
        this.flatExposure = flatExposure;
    }
    
    public static void saveData(Preferences userPref, List<FilterRowPanel> filters){
        filters.forEach((filter) ->{
            Filter values = filter.getValues();
            int nthFilter = values.getNthFilter();
            userPref.put(fieldName(FILTER_NAME, nthFilter), values.getName());
            userPref.putDouble(fieldName(FLAT_EXPOSURE, nthFilter), values.getFlatExposure());
            userPref.putInt(fieldName(FOCUS_OFFSET, nthFilter), values.getFocusOffset());
        });
    }
    
    public static void saveFocusPosition(Preferences userPref, int focusPosition){
        userPref.putInt(FOCUS_POSITION, focusPosition);
    }
    
    public static void loadData(Preferences userPref, List<FilterRowPanel> filterPanels) {
        int nth = 0;
        for (FilterRowPanel filterPanel : filterPanels){
            filterPanel.setValues(new Filter(
                    nth,
                    userPref.get(fieldName(FILTER_NAME, nth), "Empty"),
                    userPref.getDouble(fieldName(FLAT_EXPOSURE, nth), 1),
                    userPref.getInt(fieldName(FOCUS_OFFSET, nth), 0))
            );
            nth++;
        }
    }
    
    public static int loadFocusPosition(Preferences userPref){
        return userPref.getInt(FOCUS_POSITION, 3600);
    }
    
    private static String fieldName(String name, int nth){
        return name + "_" + nth;
    }
}
