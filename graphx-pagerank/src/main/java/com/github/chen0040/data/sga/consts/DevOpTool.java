package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum DevOpTool {
   Jenkins("Jenkins"),
   Ansible("Ansible"),
   Docker("Docker"),
   Mesos("Mesos"),
   Chronos("Chronos"),
   Marathon("Marathon"),
   Puppet("Puppet"),
   Chef("Chef"),
   PowerShell("PowerShell")
   ;

   private String text;
   DevOpTool(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
