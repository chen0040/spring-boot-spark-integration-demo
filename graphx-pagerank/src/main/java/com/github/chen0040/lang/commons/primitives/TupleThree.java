package com.github.chen0040.lang.commons.primitives;


import java.io.Serializable;


/**
 * Created by xschen on 12/17/15.
 */
public class TupleThree<Tone, Ttwo, Tthree> implements Serializable {
   private static final long serialVersionUID = -4073774254458691213L;

   private Tone one;
   private Ttwo two;
   private Tthree three;


   public TupleThree(Tone t1, Ttwo t2, Tthree t3) {
      one = t1;
      two = t2;
      three = t3;
   }


   public Tone _1() {
      return one;
   }


   public Ttwo _2() {
      return two;
   }

   public Tthree _3() { return three; }

   @Override public int hashCode() {
      int hash = 3001;
      hash = hash * 31 + (one == null ? 0 : one.hashCode());
      hash = hash * 31 + (two == null ? 0 : two.hashCode());
      hash = hash * 31 + (three == null ? 0 : three.hashCode());
      return hash;
   }


   @Override public boolean equals(Object obj) {
      if(obj == null) return false;
      if(!(obj instanceof TupleThree)) return false;
      TupleThree<Tone, Ttwo, Tthree> rhs = (TupleThree<Tone, Ttwo, Tthree>)obj;
      return one.equals(rhs.one) && two.equals(rhs.two) && three.equals(rhs.three);
   }


   @Override public String toString() {
      return "("+ one + ", " + two + ", " +three + ")";
   }


}
