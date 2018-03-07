package com.github.chen0040.data.commons.models;

import com.github.chen0040.lang.commons.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Created by xschen on 12/10/2016.
 */
public interface JobContract {

   String getJobTitle();

   String getCompanyName();

   String getCity();
   String getState();
   String getCountry();
   String getFormattedLocation();
   String getSource();
   LocalDateTime getDate();
   String getSnippet();
   String getUrl();
   double getLatitude();
   double getLongitude();
   String getRecordId();
   boolean getSponsored();
   boolean getExpired();
   String getFormattedLocationFull();
   String getFormattedRelativeTime();
   String getProducer();
   IndexerQuery indexerQuery();

   void setJobTitle(String jobTitle);

   void setCompanyName(String companyName);

   void setCity(String city);
   void setState(String state);
   void setCountry(String country);
   void setFormattedLocation(String formattedLocation);
   void setSource(String source);
   void setDate(LocalDateTime date);
   void setSnippet(String snippet);
   void setUrl(String url);
   void setLatitude(double latitude);
   void setLongitude(double longitude);
   void setRecordId(String recordId);
   void setSponsored(boolean sponsored);
   void setExpired(boolean expired);
   void setFormattedLocationFull(String formattedLocationFull);
   void setFormattedRelativeTime(String formattedRelativeTime);
   void setProducer(String producer);

   JobContract indexerQuery(IndexerQuery indexerQuery);


   default String trim(String text) {
      return text.replace("\\x", "");
   }

   default boolean isValid() {
      return !StringUtils.isEmpty(getRecordId());
   }

   default void copy(JobContract rhs){
      setJobTitle(rhs.getJobTitle());

      setCompanyName(rhs.getCompanyName());

      setCity(rhs.getCity());
      setState(rhs.getState());
      setCountry(rhs.getCountry());
      setFormattedLocation(rhs.getFormattedLocation());
      setSource(rhs.getSource());
      setDate(rhs.getDate());
      setSnippet(trim(rhs.getSnippet()));
      setUrl(rhs.getUrl());
      setLatitude(rhs.getLatitude());
      setLongitude(rhs.getLongitude());
      setRecordId(rhs.getRecordId());
      setSponsored(rhs.getSponsored());
      setExpired(rhs.getExpired());
      setFormattedLocationFull(rhs.getFormattedLocationFull());
      setFormattedRelativeTime(rhs.getFormattedRelativeTime());
      setProducer(rhs.getProducer());
   }

   default String addTag(String tag) {
      if(hasTag(tag)){
         return getProducer();
      }

      String producer = getProducer();
      if(StringUtils.isEmpty(producer)){
         producer = tag;
      } else {
         producer = producer.concat(",").concat(tag);
      }
      setProducer(producer);
      return getProducer();
   }

   default boolean hasTag(String tag) {
      String producer = getProducer();
      if(StringUtils.isEmpty(producer)) {
         return false;
      }
      return StringUtils.containsWholeWord(producer, tag);
   }


   /**
    * Created by xschen on 5/10/2016.
    */
   @Setter
   @Getter
   @AllArgsConstructor class CountryCode {
      private String countryName;
      private String countryCode;
   }
}
