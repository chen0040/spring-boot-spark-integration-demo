package com.github.chen0040.lang.commons.data.tables;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


/**
 * Created by xschen on 10/7/2016.
 */
public class DataRowCollection implements Serializable, Iterable<DataRow> {
    private List<DataRow> rows = new ArrayList<>();

    public DataRowCollection(){

    }

    public DataRowCollection(List<DataRow> rows){
        for(int i = 0; i < rows.size(); ++i){
            add(rows.get(i).makeCopy());
        }
    }

    public DataRow add(DataRow row){
        int rowIndex = rows.size();
        rows.add(row);
        row.setRowIndex(rowIndex);
        return row;
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public void setRows(List<DataRow> rows) {
        this.rows = rows;
    }

    public DataRow row(int index){
        return rows.get(index);
    }

    public int rowCount(){
        return rows.size();
    }

    public DataRowCollection clone(){
        DataRowCollection clone = new DataRowCollection(rows);
        return clone;
    }

    @Override
    public Iterator<DataRow> iterator() {
        return rows.iterator();
    }

    @Override
    public void forEach(Consumer<? super DataRow> action) {
        rows.forEach(action);
    }

    @Override
    public Spliterator<DataRow> spliterator() {
        return rows.spliterator();
    }


    public void addAll(List<DataRow> rows) {
        this.rows.addAll(rows);
    }


    public boolean isEmpty() {
        return rows.isEmpty();
    }
}
