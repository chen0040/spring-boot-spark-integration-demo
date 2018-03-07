package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum PHPTechnology {
   Yii("Yii"),
   Laravel("Laravel"),
   CakePHP("CAKEPHP"),

   Joomla("Joomla"),
   Drupal("Drupal"),
   Magento("Magento"),
   CodeIgniter("CodeIgniter"),
   cPanel("cPanel"),
   Wordpress("Wordpress")
   ;

   private String text;
   PHPTechnology(String text){
      this.text = text;
   }
   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }
}
