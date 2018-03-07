package com.github.chen0040.data.sga.repositories;


import com.github.chen0040.data.commons.models.JobContract;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 5/10/2016.
 */
@Repository
public class CountryCodeRepositoryEmbedded implements CountryCodeRepository {
   Map<String, String> countryCodes = new HashMap<>();
   List<JobContract.CountryCode> mCodeList = new ArrayList<>();
   Map<String, JobContract.CountryCode> mLookup = new HashMap<>();

   @Override
   public List<JobContract.CountryCode> findAll()
   {
      if (mCodeList.isEmpty())
      {
            for (String country_name : countryCodes.keySet())
            {
               JobContract.CountryCode cc=new JobContract.CountryCode(country_name, countryCodes.get(country_name));
               mLookup.put(country_name, cc);
               mCodeList.add(cc);
            }
      }

      return mCodeList;
   }

   @Override
   public JobContract.CountryCode getSaudiArabia()
   {
      return mLookup.get("Saudi Arabia");
   }

   @Override
   public JobContract.CountryCode getSingapore()
   {
      return mLookup.get("Singapore");
   }

   public CountryCodeRepositoryEmbedded()
   {
      countryCodes.put("United States", "us");
      countryCodes.put("Argentina", "ar");
      countryCodes.put("Australia", "au");
      countryCodes.put("Austria", "at");
      countryCodes.put("Bahrain", "bh");
      countryCodes.put("Belgium", "be");
      countryCodes.put("Brazil", "br");
      countryCodes.put("Canada", "ca");
      countryCodes.put("Chile", "cl");
      countryCodes.put("China", "cn");
      countryCodes.put("Colombia", "co");
      countryCodes.put("Czech Republic", "cz");
      countryCodes.put("Denmark", "dk");
      countryCodes.put("Finland", "fi");
      countryCodes.put("France", "fr");
      countryCodes.put("Germany", "de");
      countryCodes.put("Greece", "gr");
      countryCodes.put("Hong Kong", "hk");
      countryCodes.put("Hungary", "hu");
      countryCodes.put("India", "in");
      countryCodes.put("Indonesia", "id");
      countryCodes.put("Ireland", "ie");
      countryCodes.put("Israel", "il");
      countryCodes.put("Italy", "it");
      countryCodes.put("Japan", "jp");
      countryCodes.put("Korea", "kr");
      countryCodes.put("Kuwait", "kw");
      countryCodes.put("Luxembourg", "lu");
      countryCodes.put("Malaysia", "my");
      countryCodes.put("Mexico", "mx");
      countryCodes.put("Netherlands", "nl");
      countryCodes.put("New Zealand", "nz");
      countryCodes.put("Norway", "no");
      countryCodes.put("Oman", "om");
      countryCodes.put("Pakistan", "pk");
      countryCodes.put("Peru", "pe");
      countryCodes.put("Philippines", "ph");
      countryCodes.put("Poland", "pl");
      countryCodes.put("Portugal", "pt");
      countryCodes.put("Qatar", "qa");
      countryCodes.put("Romania", "ro");
      countryCodes.put("Russia", "ru");
      countryCodes.put("Saudi Arabia", "sa");
      countryCodes.put("Singapore", "sg");
      countryCodes.put("South Africa", "za");
      countryCodes.put("Spain", "es");
      countryCodes.put("Sweden", "se");
      countryCodes.put("Switzerland", "ch");
      countryCodes.put("Taiwan", "tw");
      countryCodes.put("Turkey", "tr");
      countryCodes.put("United Arab Emirates", "ae");
      countryCodes.put("United Kingdom", "gb");
      countryCodes.put("Venezuela", "ve");
   }

   @Override
   public String findCountryCodeByCountryName(String country_name)
   {
      if (countryCodes.containsKey(country_name))
      {
         return countryCodes.get(country_name);
      }
      return null;
   }
}
