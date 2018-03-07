package com.github.chen0040.data.sga.repositories;



import com.github.chen0040.data.commons.models.JobContract;

import java.util.List;


/**
 * Created by xschen on 5/10/2016.
 */
public interface CountryCodeRepository {
   List<JobContract.CountryCode> findAll();

   JobContract.CountryCode getSaudiArabia();

   JobContract.CountryCode getSingapore();

   String findCountryCodeByCountryName(String country_name);
}
