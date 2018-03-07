package com.github.chen0040.lang.commons.primitives;


import java.io.Serializable;


/**
 * Created by xschen on 12/17/15.
 */
public class TupleTwo<Tone, Ttwo> implements Serializable {
   private static final long serialVersionUID = -4073774254458691213L;

   private Tone one;
   private Ttwo two;


   public TupleTwo(Tone t1, Ttwo t2) {
      one = t1;
      two = t2;
   }


   public Tone _1() {
      return one;
   }


   public Ttwo _2() {
      return two;
   }


   @Override public int hashCode() {
      int hash = 3001;
      hash = hash * 31 + (one == null ? 0 : one.hashCode());
      hash = hash * 31 + (two == null ? 0 : two.hashCode());
      return hash;
   }


   @Override public boolean equals(Object obj) {
      if(obj == null) return false;
      if(!(obj instanceof TupleTwo)) return false;
      TupleTwo<Tone, Ttwo> rhs = (TupleTwo<Tone, Ttwo>)obj;
      return one.equals(rhs.one) && two.equals(rhs.two);
   }


   @Override public String toString() {
      return "("+ one + ", " + two + ")";
   }


}
