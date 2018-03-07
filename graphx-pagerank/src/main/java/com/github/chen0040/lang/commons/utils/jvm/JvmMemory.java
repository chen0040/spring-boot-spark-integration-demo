package com.github.chen0040.lang.commons.utils.jvm;


/**
 * Created by xschen on 10/3/16.
 */
public class JvmMemory {
   private double allocatedMemoryInMB;
   private double freeMemoryInMB;
   private double maxMemoryInMB;
   private double usedMemoryInMB;

   public JvmMemory(double allocatedMemoryInMB,
           double freeMemoryInMB,
           double maxMemoryInMB,
           double usedMemoryInMB
           ){
      this.allocatedMemoryInMB = allocatedMemoryInMB;
      this.freeMemoryInMB = freeMemoryInMB;
      this.maxMemoryInMB = maxMemoryInMB;
      this.usedMemoryInMB = usedMemoryInMB;
   }


   public double getAllocatedMemoryInMB() {
      return allocatedMemoryInMB;
   }


   public double getFreeMemoryInMB() {
      return freeMemoryInMB;
   }


   public double getMaxMemoryInMB() {
      return maxMemoryInMB;
   }


   public double getUsedMemoryInMB() {
      return usedMemoryInMB;
   }
}
