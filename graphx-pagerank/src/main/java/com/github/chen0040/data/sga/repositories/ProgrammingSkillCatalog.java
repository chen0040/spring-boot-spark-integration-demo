package com.github.chen0040.data.sga.repositories;



import com.github.chen0040.data.sga.consts.*;
import com.github.chen0040.data.commons.enums.SkillType;

import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 12/10/2016.
 */
public interface ProgrammingSkillCatalog {


   List<ProgrammingLanguage> getProgrammingLanguages();
   List<BigDataTechnology> getBigDataTechnologies();
   List<DevOpTool> getDevOpTools();
   List<JavaBuildTool> getJavaBuildTools();
   List<JavaScriptTechnology> getJavaScriptTechnologies();
   List<JavaTechnology> getJavaTechnologies();
   List<JavaTestTechnology> getJavaTestTechnologies();
   List<MicrosoftTechnology> getMicrosoftTechnologies();
   List<SoftwareEngineering> getSoftwareEngineering();
   List<SoftwareQualityTool> getSoftwareQualityTools();
   List<WebTechnology> getWebTechnologies();
   List<PHPTechnology> getPHPTechnologies();
   List<PythonTechnology> getPythonTechnologies();
   List<GameDevTechnology> getGameDevTechnologies();

   Map<String, SkillType> findAll();

}
