package com.github.chen0040.lang.commons.data.tables;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;


/**
 * Created by xschen on 10/7/2016.
 */
public class DataColumnCollection implements Serializable, Iterable<DataColumn> {
    private List<DataColumn> columns = new ArrayList<>();
    private Map<String, Integer> columNames = new HashMap<>();

    public DataColumnCollection(){

    }

    public DataColumnCollection(List<DataColumn> columns){
        for(int i=0; i < columns.size(); ++i){
            add(columns.get(i).clone());
        }
    }

    public DataColumn add(String columnName){
        DataColumn column = new DataColumn(columnName);
        return add(column);
    }

    public DataColumn add(DataColumn column){
        int columnIndex = columns.size();
        columns.add(column);
        columNames.put(column.getName(), columnIndex);
        column.setColumnIndex(columnIndex);

        return column;
    }

    public int columnCount(){
        return columns.size();
    }

    public List<DataColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DataColumn> columns) {
        this.columns = columns;
    }

    public Map<String, Integer> getColumNames() {
        return columNames;
    }

    public void setColumNames(Map<String, Integer> columNames) {
        this.columNames = columNames;
    }

    public DataColumnCollection makeCopy(){
        DataColumnCollection clone = new DataColumnCollection(columns);
        return clone;
    }

    @Override
    public Iterator<DataColumn> iterator() {
        return columns.iterator();
    }

    @Override
    public void forEach(Consumer<? super DataColumn> action) {
        columns.forEach(action);
    }

    @Override
    public Spliterator<DataColumn> spliterator() {
        return columns.spliterator();
    }

    public int indexOf(String name) {
        return columNames.getOrDefault(name, -1);
    }

    public DataColumn column(String name){
        return column(indexOf(name));
    }

    public DataColumn column(int index){
        return columns.get(index);
    }
}
