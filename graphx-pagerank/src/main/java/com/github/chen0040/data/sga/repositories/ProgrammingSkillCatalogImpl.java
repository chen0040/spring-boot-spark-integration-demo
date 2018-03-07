package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.sga.consts.*;
import com.github.chen0040.data.commons.enums.SkillType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 12/10/2016.
 */
@Repository
public class ProgrammingSkillCatalogImpl implements ProgrammingSkillCatalog {


   @Override public List<ProgrammingLanguage> getProgrammingLanguages() {
      return Arrays.asList(ProgrammingLanguage.values());
   }


   @Override public List<BigDataTechnology> getBigDataTechnologies() {
      return Arrays.asList(BigDataTechnology.values());
   }


   @Override public List<DevOpTool> getDevOpTools() {
      return Arrays.asList(DevOpTool.values());
   }


   @Override public List<JavaBuildTool> getJavaBuildTools() {
      return Arrays.asList(JavaBuildTool.values());
   }


   @Override public List<JavaScriptTechnology> getJavaScriptTechnologies() {
      return Arrays.asList(JavaScriptTechnology.values());
   }


   @Override public List<JavaTechnology> getJavaTechnologies() {
      return Arrays.asList(JavaTechnology.values());
   }


   @Override public List<JavaTestTechnology> getJavaTestTechnologies() {
      return Arrays.asList(JavaTestTechnology.values());
   }


   @Override public List<MicrosoftTechnology> getMicrosoftTechnologies() {
      return Arrays.asList(MicrosoftTechnology.values());
   }


   @Override public List<SoftwareEngineering> getSoftwareEngineering() {
      return Arrays.asList(SoftwareEngineering.values());
   }


   @Override public List<SoftwareQualityTool> getSoftwareQualityTools() {
      return Arrays.asList(SoftwareQualityTool.values());
   }


   @Override public List<WebTechnology> getWebTechnologies() {
      return Arrays.asList(WebTechnology.values());
   }


   @Override public List<PHPTechnology> getPHPTechnologies() {
      return Arrays.asList(PHPTechnology.values());
   }


   @Override public List<PythonTechnology> getPythonTechnologies() {
      return Arrays.asList(PythonTechnology.values());
   }


   @Override public List<GameDevTechnology> getGameDevTechnologies() {
      return Arrays.asList(GameDevTechnology.values());
   }


   @Override public Map<String, SkillType> findAll() {
      Map<String, SkillType> result = new HashMap<>();
      result.putAll(convert(getBigDataTechnologies(), SkillType.BigDataTechnology));
      result.putAll(convert(getDevOpTools(), SkillType.DevOpTool));
      result.putAll(convert(getJavaBuildTools(), SkillType.JavaBuildTool));
      result.putAll(convert(getJavaScriptTechnologies(), SkillType.JavaScriptTechnology));
      result.putAll(convert(getJavaTechnologies(), SkillType.JavaTechnology));
      result.putAll(convert(getJavaTestTechnologies(), SkillType.JavaTestTechnology));
      result.putAll(convert(getMicrosoftTechnologies(), SkillType.MicrosoftTechnology));
      result.putAll(convert(getPHPTechnologies(), SkillType.PHPTechnology));
      result.putAll(convert(getProgrammingLanguages(), SkillType.ProgrammingLanguage));
      result.putAll(convert(getPythonTechnologies(), SkillType.PythonTechnology));
      result.putAll(convert(getSoftwareEngineering(), SkillType.SoftwareEngineering));
      result.putAll(convert(getSoftwareQualityTools(), SkillType.SoftwareQualityTool));
      result.putAll(convert(getWebTechnologies(), SkillType.WebTechnology));
      result.putAll(convert(getGameDevTechnologies(), SkillType.GameDevTechnology));

      return result;
   }




   private <T> Map<String, SkillType> convert(List<T> skills, SkillType type){
      Map<String, SkillType> result = new HashMap<>();
      skills.stream().map(Object::toString).forEach(skill -> result.put(skill, type));
      return result;
   }
}
