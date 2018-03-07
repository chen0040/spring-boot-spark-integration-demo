package com.github.chen0040.lang.commons.data.tuples;


import com.github.chen0040.lang.commons.consts.TrainingLabels;
import com.github.chen0040.lang.commons.primitives.CopyCat;
import com.github.chen0040.lang.commons.utils.CollectionUtil;
import com.github.chen0040.lang.commons.utils.NumberUtils;
import com.github.chen0040.lang.commons.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 12/10/2016.
 */
public class DataTuple implements Serializable,CopyCat<DataTuple> {
   private static final long serialVersionUID = -4897049586495955672L;
   private Map<Integer, Double> values = new HashMap<>();
   private String label;
   private String predictedLabel;
   private double outputValue = Double.NaN;
   private double predictedOutputValue;
   private static DataTupleFactory factory;
   private int length = 0;



   public DataTuple(Map<Integer, Double> row, int length) {
      this.length = length;
      for(Map.Entry<Integer, Double> entry : row.entrySet()){
         setValueAt(entry.getKey(), entry.getValue());
      }
   }

   public DataTuple(int length) {
      this.length = length;
   }

   public DataTuple(){

   }


   public String getPredictedLabel(){
      return predictedLabel;
   }

   public void setPredictedLabel(String predictedLabel) {
      this.predictedLabel = predictedLabel;
   }


   public List<Double> getValues() {
      List<Double> result = new ArrayList<>();
      for(int i=0; i < length; ++i){
         result.add(values.getOrDefault(i, 0.0));
      }
      return result;
   }


   public void setValues(List<Double> values)
   {
      this.values.clear();
      for(int i=0; i < values.size(); ++i){
         double val = values.get(i);
         if(!NumberUtils.isZero(val)) {
            this.values.put(i, val);
         }
      }
   }


   public String getLabel() {
      return label;
   }


   public void setLabel(String label) {
      this.label = label;
   }


   public double getOutputValue() {
      return outputValue;
   }


   public void setOutputValue(double outputValue) {
      this.outputValue = outputValue;
   }


   public double getPredictedOutputValue() {
      return predictedOutputValue;
   }


   public void setPredictedOutputValue(double predictedOutputValue) {
      this.predictedOutputValue = predictedOutputValue;
   }


   public int tupleLength() {
      return length;
   }

   public void setValueAt(int index, double value){
      if(NumberUtils.isZero(value)){
         values.remove(index);
      } else {
         values.put(index, value);
      }
      length = Math.max(length, index+1);
   }

   public double getValueAt(int index) {
      return values.getOrDefault(index, 0.0);
   }

   public static void setFactory(DataTupleFactory f) {
      factory = f;
   }

   public static synchronized DataTupleFactory getFactory(){
      if(factory == null) {
         factory = new DataTupleFactoryImpl();
      }
      return factory;
   }


   public double[] toNumericArray() {

      double[] arr = new double[length];
      for(int i=0; i < length; ++i){
         arr[i] = values.getOrDefault(i, 0.0);
      }
      return arr;
   }


   public boolean hasLabel() {
      return !StringUtils.isEmpty(label);
   }


   public boolean isLabeledPositive() {
      return label.equals(TrainingLabels.POSITIVE_LABEL);
   }


   public boolean hasOutputValue() {
      return !Double.isNaN(outputValue);
   }


   public void setNegativeLabel() {
      setLabel(TrainingLabels.NEGATIVE_LABEL);
   }

   public void setPositiveLabel(){
      setLabel(TrainingLabels.POSITIVE_LABEL);
   }


   @Override public DataTuple makeCopy() {
      DataTuple clone = new DataTuple();
      clone.copy(this);
      return clone;
   }


   @Override public void copy(DataTuple rhs) {
      values = CollectionUtil.makeCopy(rhs.values);
      label = rhs.label;
      predictedLabel = rhs.predictedLabel;
      outputValue = rhs.outputValue;
      predictedOutputValue = rhs.predictedOutputValue;
   }

   @Override
   public String toString(){
      double[] array = toNumericArray();
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      for(int i=0; i < array.length; ++i){
         if(i != 0){
            sb.append(", ");
         }
         sb.append(array[i]);
      }
      sb.append("]");
      return sb.toString();
   }
}
