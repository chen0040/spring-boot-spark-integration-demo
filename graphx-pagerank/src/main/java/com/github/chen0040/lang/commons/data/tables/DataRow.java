package com.github.chen0040.lang.commons.data.tables;

import com.github.chen0040.lang.commons.data.sql.SQLColumn;
import com.github.chen0040.lang.commons.data.tuples.DataTuple;
import com.github.chen0040.lang.commons.utils.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 10/7/2016.
 */
public class DataRow implements Serializable {
    private DataColumnCollection columns = new DataColumnCollection();

    private Map<Integer, Double> data = new HashMap<>();

    private String predictedLabel = null;
    private String label = "";

    private double predictedOutputValue = 0;
    private double outputValue = 0;
    private int rowIndex = -1;

    private Object targetRefSqlColumnValue;
    private SQLColumn targetRefSqlColumn;
    private SQLColumn targetSqlColumn;
    private List<SQLColumn> sqlColumns;


    public DataRow(){

    }

    public DataRow(DataColumnCollection columns){
        this.columns = columns;
    }

    public DataRow makeCopy(){
        DataRow clone = new DataRow(columns.makeCopy());
        clone.data.putAll(data);

        clone.predictedLabel = predictedLabel;
        clone.label = label;

        clone.predictedOutputValue = predictedOutputValue;
        clone.outputValue = outputValue;

        clone.rowIndex = rowIndex;

        clone.targetRefSqlColumnValue = targetRefSqlColumnValue;

        if(targetRefSqlColumn != null) {
            clone.targetRefSqlColumn = targetRefSqlColumn.makeCopy();
        }

        if(targetSqlColumn != null) {
            clone.targetSqlColumn = targetSqlColumn.makeCopy();
        }

        if(sqlColumns != null){
            clone.sqlColumns = new ArrayList<>();
            for(int i=0; i < sqlColumns.size(); ++i){
                clone.sqlColumns.add(sqlColumns.get(i));
            }
        }

        return clone;
    }

    public void setTargetRefSqlColumnValue(Object targetRefSqlColumnValue) {
        this.targetRefSqlColumnValue = targetRefSqlColumnValue;
    }

    public Object getTargetRefSqlColumnValue(){
        return targetRefSqlColumnValue;
    }


    public SQLColumn getTargetRefSqlColumn() {
        return targetRefSqlColumn;
    }


    public SQLColumn getTargetSqlColumn() {
        return targetSqlColumn;
    }


    public void setTargetSqlColumn(SQLColumn targetSqlColumn) {
        this.targetSqlColumn = targetSqlColumn;
    }


    public void setTargetRefSqlColumn(SQLColumn targetRefSqlColumn) {
        this.targetRefSqlColumn = targetRefSqlColumn;
    }


    public Map<Integer, Double> getData() {
        return data;
    }

    public void setData(Map<Integer, Double> data) {
        this.data = data;
    }

    public void setColumns(DataColumnCollection columns) {
        this.columns = columns;
    }

    public String getPredictedLabel() {
        return predictedLabel;
    }

    public void setPredictedLabel(String predictedLabel) {
        this.predictedLabel = predictedLabel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPredictedOutputValue() {
        return predictedOutputValue;
    }

    public void setPredictedOutputValue(double predictedOutputValue) {
        this.predictedOutputValue = predictedOutputValue;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public DataColumnCollection getColumns() {
        return columns;
    }

    public double cell(String name){
        int columnIndex = columns.indexOf(name);
        return cell(columnIndex);
    }

    public void cell(String name, double value){
        int columnIndex = columns.indexOf(name);
        if(columnIndex != -1) {
            cell(columnIndex, value);
        }
    }

    public void cell(int columnIndex, double value){
        if(!NumberUtils.isZero(value)) {
            data.put(columnIndex, value);
        } else {
            data.remove(columnIndex);
        }
    }

    public double cell(int columnIndex){
        return data.getOrDefault(columnIndex, 0.0);
    }

    public int columnCount(){
        return columns.columnCount();
    }

    public double[] cells() {
        int count = columns.columnCount();
        double[] values = new double[count];
        for(int i=0; i < count; ++i){
            values[i] = cell(i);
        }

        return values;

    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }



    public DataTuple toTuple() {


        DataTuple tuple = new DataTuple(data, columnCount());
        tuple.setLabel(label);
        tuple.setOutputValue(outputValue);
        tuple.setPredictedLabel(predictedLabel);
        tuple.setPredictedOutputValue(predictedOutputValue);

        return tuple;


    }

    @Override
    public String toString(){
        return toTuple().toString();
    }


    public void setSqlColumns(List<SQLColumn> sqlColumns) {
        this.sqlColumns = sqlColumns;
    }


    public List<SQLColumn> getSqlColumns() {
        return sqlColumns;
    }

    public boolean isTargetValid4Sql() {
        return targetRefSqlColumn != null && targetSqlColumn  != null && targetRefSqlColumnValue != null;
    }


    public String makeSql4PredictionUpdate() {
        String tableName = targetRefSqlColumn.getTableName();
        return "UPDATE " + tableName + " SET " + targetSqlColumn.getName() + " = ? WHERE " + targetRefSqlColumn.getName() + " = ? ";
    }
}
