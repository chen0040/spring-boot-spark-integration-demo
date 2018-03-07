package com.github.chen0040.lang.commons.utils;


import com.github.chen0040.lang.commons.primitives.TupleTwo;

import java.util.*;
import java.util.function.Predicate;


/**
 * Created by xschen on 26/7/2016.
 */
public class CollectionUtil {

   public static String[] toArray(List<String> values) {
      if (values == null) {
         return null;
      }
      int len = values.size();
      String[] clone = new String[len];
      for (int i = 0; i < len; ++i) {
         clone[i] = values.get(i);
      }
      return clone;
   }

   public static <T> List<T> newArrayList(int size, T defaultValue){
      List<T> list = new ArrayList<>(size);
      for(int i=0; i < size; ++i){
         list.add(defaultValue);
      }
      return list;
   }


   public static double[][] makeCopy(double[][] rhs) {
      if (rhs == null)
         return null;

      int len1 = rhs.length;
      double[][] clone = new double[len1][];
      for (int i = 0; i < len1; ++i) {
         int len2 = rhs[i].length;
         clone[i] = new double[len2];
         System.arraycopy(rhs[i], 0, clone[i], 0, len2);
      }
      return clone;
   }

   public static <K, V> Map<K, V> makeCopy(Map<K, V> rhs){
      Map<K, V> clone = new HashMap<>();
      clone.putAll(rhs);
      return clone;
   }


   public static double[] makeCopy(double[] rhs) {
      if (rhs == null)
         return null;

      int len = rhs.length;
      double[] clone = new double[len];
      System.arraycopy(rhs, 0, clone, 0, len);
      return clone;
   }


   public static int[] makeCopy(int[] rhs) {
      if (rhs == null)
         return null;

      int len = rhs.length;
      int[] clone = new int[len];
      System.arraycopy(rhs, 0, clone, 0, len);
      return clone;
   }

   public static <T> List<T> makeCopy(List<T> rhs) {
      if (rhs == null)
         return null;

      int len = rhs.size();
      List<T> clone = new ArrayList<>(len);
      for(int i=0; i < len; ++i){
         clone.add(rhs.get(i));
      }
      return clone;
   }


   public static void incValue(List<Integer> topicCounts, int topicIndex) {
      if(topicCounts.size() <= topicIndex) return;

      int currValue = topicCounts.get(topicIndex);
      if(currValue < Integer.MAX_VALUE) {
         topicCounts.set(topicIndex, currValue + 1);
      }
   }

   public static void decValue(List<Integer> topicCounts, int topicIndex) {
      int current = topicCounts.get(topicIndex);
      if(current > 0) {
         topicCounts.set(topicIndex, current - 1);
      }
   }


   public static <T> List<T> expandArrayList(List<T> list, int newSize, T defaultValue) {
      while(list.size() < newSize){
         list.add(defaultValue);
      }
      return list;
   }


   public static <T> boolean equals(List<T> list1, List<T> list2) {
      if(list1.size() != list2.size()) {
         return false;
      }
      for(int i=0; i < list1.size(); ++i){
         if(!list1.get(i).equals(list2.get(i))){
            return false;
         }
      }
      return true;
   }


   public static <K, V> boolean equals(Map<K, V> map1, Map<K, V> map2) {
      if(map1.size() != map2.size()){
         return false;
      }

      for(Map.Entry<K, V> entry: map1.entrySet()){
         if(!map2.containsKey(entry.getKey())){
            return false;
         }
         if(!entry.getValue().equals(map2.get(entry.getKey()))){
            return false;
         }
      }

      return true;
   }


   public static List<List<String>> generateCombinations(List<String> items) {
      List<List<String>> combination = new ArrayList<>();

      List<List<String>> sets = new ArrayList<>();

      for(int i=0; i < items.size(); ++i){
         List<String> set = new ArrayList<>();
         set.add(items.get(i));
         sets.add(set);
      }
      combination.addAll(sets);

      while(!sets.isEmpty()){
         List<List<String>> newSets = new ArrayList<>();

         for(int j=0; j < sets.size(); ++j){
            List<String> setj = sets.get(j);
            for(int k=0; k < sets.size(); ++k) {
               if(j == k) continue;

               List<String> setk = sets.get(k);

               boolean shouldCombine = true;
               for(int l=0; l < setj.size()-1; ++l){
                  if(!setj.get(l).equals(setk.get(l))){
                     shouldCombine = false;
                     break;
                  }
               }

               if(shouldCombine && setj.get(setj.size()-1).compareTo(setk.get(setk.size()-1)) < 0){
                  List<String> setm = new ArrayList<>();
                  setm.addAll(setj);
                  setm.add(setk.get(setk.size()-1));
                  newSets.add(setm);
               }
            }
         }

         if(!newSets.isEmpty()) {
            combination.addAll(newSets);
         }
         sets = newSets;
      }

      return combination;

   }


   public static List<String> toList(String[] split) {
      List<String> result = new ArrayList<>();
      for(int i=0; i < split.length; ++i){
         result.add(split[i].trim());
      }
      return result;
   }


   public static <T> List<T> toList(Iterable<T> all) {
      List<T> result = new ArrayList<>();
      for(T t : all) {
         result.add(t);
      }
      return result;
   }


   public static <T> Optional<T> toOptional(T result) {
      if(result == null) return Optional.empty();
      return Optional.of(result);
   }


   public static <K, V> V getOrCreate(Map<K, V> map, K key, V defaultValue) {
      if(map.containsKey(key)) {
         return map.get(key);
      }
      map.put(key, defaultValue);
      return defaultValue;
   }

   public static <K, V> List<TupleTwo<K, V>> filter(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
      List<TupleTwo<K, V>> result = new ArrayList<>();

      for(Map.Entry<K, V> entry : map.entrySet()) {
         if(predicate.test(entry)) {
            result.add(new TupleTwo<>(entry.getKey(), entry.getValue()));
         }
      }

      return result;
   }


   public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {
      endIndex = Math.min(endIndex, list.size());
      List<T> result = new ArrayList<>();
      for(int i=startIndex; i < endIndex; ++i) {
         result.add(list.get(i));
      }
      return result;
   }


   public static <T> List<T> truncate(List<T> list, int maxSize) {
      if(list.size() > maxSize){
         List<T> result = new ArrayList<>();
         for(int i=0; i < maxSize; ++i) {
            result.add(list.get(i));
         }
         return result;
      }

      return list;
   }
}
