package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.sga.repositories.CountryCodeRepository;
import com.github.chen0040.data.commons.models.JobContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by xschen on 5/10/2016.
 */
@Service
public class CountryCodeServiceImpl implements CountryCodeService {
   @Autowired
   CountryCodeRepository countryCodeRepository;


   @Override public List<JobContract.CountryCode> findAll() {
      return countryCodeRepository.findAll();
   }


   @Override public JobContract.CountryCode getSaudiArabia() {
      return countryCodeRepository.getSaudiArabia();
   }


   @Override public JobContract.CountryCode getSingapore() {
      return countryCodeRepository.getSingapore();
   }


   @Override public String findCountryCodeByCountryName(String country_name) {
      return countryCodeRepository.findCountryCodeByCountryName(country_name);
   }
}
