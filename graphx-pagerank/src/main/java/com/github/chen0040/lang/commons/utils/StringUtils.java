package com.github.chen0040.lang.commons.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by xschen on 10/5/16.
 */
public class StringUtils {
   public static boolean isEmpty(String str) {
      return str == null || str.equals("");
   }


   public static String truncate(String s, int maxLength) {
      if(s.length() > maxLength){
         return s.substring(0, maxLength);
      }
      return s;
   }


   public static String formatDate(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      if(date == null) {
         date = new Date(0L);
      }
      return dateFormat.format(date);
   }

   public static String formatDateTime(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if(date == null) {
         date = new Date(0L);
      }
      return dateFormat.format(date);
   }


   public static String formatLong(long value) {
      return "" + value;
   }


   public static String formatBoolean(boolean value) {

      return value ? "true" : "false";
   }


   public static String formatInteger(int value) {
      return "" + value;
   }


   public static Date parseDate(String value) {

      List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
      knownPatterns.add(new SimpleDateFormat("dd-MM-yyyy"));
      knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy"));

      for (SimpleDateFormat dateFormat : knownPatterns) {
         try {
            // Take a try
//            System.out.println("Trying dateFormat: " + dateFormat.toPattern());
            return dateFormat.parse(value);

         } catch (ParseException pe) {
            // Loop on
         }
      }
      System.err.println("No known Date format found for: " + value + ". Setting date to 0..");
//      String toPrint = "";
//      for (SimpleDateFormat dateFormat : knownPatterns) {
//         toPrint += (dateFormat.toPattern() + " - ");
//      }
//      System.err.println("Checked formats: " + toPrint );
      return new Date(0L);

   }


   public static long parseLong(String value) {
      long result;
      try{
         result = Long.parseLong(value);
      }catch(NumberFormatException ex) {
         result = -1L;
      }
      return result;
   }


   public static boolean parseBoolean(String value) {
      if(StringUtils.isEmpty(value)) return false;
      if(value.equalsIgnoreCase("true")) {
         return true;
      } else if(value.equalsIgnoreCase("yes")) {
         return true;
      } else if(value.equalsIgnoreCase("y")) {
         return true;
      } else if(value.equals("1")) {
         return true;
      }
      return false;
   }


   public static int parseInteger(String value) {
      int result;
      try{
         result = Integer.parseInt(value);
      } catch(NumberFormatException ex) {
         result = 0;
      }
      return result;
   }


   public static boolean isAlphaNumeric(char c) {
      return Character.isDigit(c) || Character.isLetter(c);
   }


   public static Double parseDouble(String value) {
      double result;
      try{
         result = Double.parseDouble(value);
      }catch(NumberFormatException ex) {
         result = -1;
      }
      return result;
   }


   public static String replace(String text, String oldString, String newString) {
      if(isEmpty(text)){
         return text;
      } else {
         return text.replace(oldString, newString);
      }
   }

   public static boolean containsWholeWord(String text, String word) {

      if(StringUtils.isEmpty(text) || text.length() < word.length()) return false;

      int index = text.indexOf(word);
      if(index == -1) return false;
      int prevCharIndex = index -1;
      int nextCharIndex = index + word.length();
      if(prevCharIndex >= 0 && StringUtils.isAlphaNumeric(text.charAt(prevCharIndex))) return false;
      if(nextCharIndex < text.length() && StringUtils.isAlphaNumeric(text.charAt(nextCharIndex))) return false;

      return true;

   }

}
