package com.github.chen0040.data.sga.consts;


/**
 * Created by xschen on 16/10/2016.
 */
public enum GameDevTechnology {
   Unity3D("Unity3D"),
   DirectX("DirectX"),
   OpenGL("OpenGL"),
   Unreal("Unreal"),
   MonoGame("MonoGame"),
   Cocos2d("Cocos2d"),
   Allegro("Allegro"),
   OGRE("OGRE"),
   SDL("SDL"),
   GLUT("GLUT"),
   GLUI("GLUI")
   ;

   private String text;
   GameDevTechnology(String text){
      this.text = text;
   }
   public String getText(){
      return text;
   }
   @Override
   public String toString(){
      return text;
   }
}
