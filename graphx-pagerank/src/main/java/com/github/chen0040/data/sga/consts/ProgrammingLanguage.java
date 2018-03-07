package com.github.chen0040.data.sga.consts;


import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 16/10/2016.
 */
public enum ProgrammingLanguage {
   Java("Java"),
   Cpp("C++"),
   C("C"),
   CSharp("C#"),
   Python("Python"),
   Ruby("Ruby"),
   PHP("PHP"),
   JAVASCRIPT("JavaScript"),
   GoLang("GoLang"),
   Scala("Scala"),
   PERL("PERL"),
   VisualBasic("Visual Basic"),
   SHELL_SCRIPT("Shell Script"),
   COBOL("COBOL"),
   FSharp("F#"),
   Groovy("Groovy"),
   awk("awk"),
   sed("sed"),
   Clojure("Clojure"),
   Elixir("Elixir"),
   Erlang("Erlang"),
   Euphoria("Euphoria"),
   Fortran("Fortran"),
   LISP("LISP"),
   Matlab("Matlab"),
   ObjectiveC("Objective-C"),
   Pascal("Pascal"),
   R("R"),
   SAS("SAS"),
   Rexx("Rexx"),
   SocketIO("Socket.IO"),
   Swift("Swift"),
   VBA("VBA"),
   JCL("JCL"),
   Assembly("Assembly"),
   SQL("SQL"),
   TSQL("T-SQL"),
   RSpec("RSpec"),
   Unix("Unix"),
   Linux("Linux"),
   TCL("TCL"),
   Lua("Lua"),
   Apex("Apex");

   private String text;
   ProgrammingLanguage(String text){
      this.text = text;
   }

   public String getText(){
      return text;
   }

   @Override public String toString() {
      return text;
   }

}
