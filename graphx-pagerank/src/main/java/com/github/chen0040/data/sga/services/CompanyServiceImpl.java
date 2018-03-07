package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import com.github.chen0040.data.sga.models.Company;
import com.github.chen0040.data.sga.models.CompanyNetStat;
import com.github.chen0040.data.sga.repositories.CompanyNetStatRepository;
import com.github.chen0040.data.sga.repositories.CompanyRepository;
import com.github.chen0040.data.sga.specs.SingleSpecification;
import com.github.chen0040.data.sga.specs.SearchCriteria;
import com.github.chen0040.lang.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 3/10/2016.
 */
@Service
public class CompanyServiceImpl implements CompanyService {
   @Autowired
   private CompanyRepository companyRepository;


   @Autowired
   private CompanyNetStatRepository companyNetStatRepository;

   @Transactional
   @Override public Company save(Company company) {
      return companyRepository.save(company);
   }


   @Transactional
   @Override public Optional<Company> deleteById(long companyId) {
      Company c = companyRepository.findOne(companyId);
      if(c == null) {
         return Optional.empty();
      } else {
         companyRepository.delete(companyId);
         companyNetStatRepository.delete(c.getName());

         return Optional.of(c);
      }
   }


   @Override public Optional<Company> findByCompanyName(String companyName) {
      Company company = companyRepository.findByName(companyName);
      if(company == null) {
         return Optional.empty();
      }

      return Optional.of(company);
   }



   @Transactional
   @Override public void saveSimilarCompanies(String companyName, List<OneToManyToOneAssociation> collection) {


      CompanyNetStat stat = companyNetStatRepository.findOne(companyName);

      if(stat == null) {
         stat = new CompanyNetStat();
         stat.setCompanyName(companyName);
         stat.makeAssociations(collection);
      } else {
         stat.makeAssociations(collection);
      }
      companyNetStatRepository.save(stat);
   }


   @Override public CompanyNetStat findNetStat(String companyName) {
      return companyNetStatRepository.findOne(companyName);
   }


   @Transactional
   @Override public void saveRank(String companyName, double rank) {
      CompanyNetStat stat = companyNetStatRepository.findOne(companyName);
      if(stat == null) {
         CompanyNetStat companyNetStat = new CompanyNetStat();
         companyNetStat.setRank(rank);
         companyNetStat.setCompanyName(companyName);
         stat = companyNetStat;
      } else {
         stat.setRank(rank);
      }

      companyNetStatRepository.save(stat);
   }


   @Override public long countNetStats() {
      return companyNetStatRepository.count();
   }


   @Transactional
   @Override public void saveClusterId(String companyName, long clusterId) {
      CompanyNetStat stat = companyNetStatRepository.findOne(companyName);
      if(stat == null) {
         CompanyNetStat companyNetStat = new CompanyNetStat();
         companyNetStat.setClusterId(clusterId);
         companyNetStat.setCompanyName(companyName);
         stat = companyNetStat;
      } else {
         stat.setClusterId(clusterId);
      }

      companyNetStatRepository.save(stat);
   }


   @Transactional
   @Override public Company saveCompanyByName(String companyName, String link) {
      Company company = new Company();
      company.setName(companyName);
      company.setLink(link);

      save(company);

      return company;
   }


   @Override public Optional<Company> findByCompanyId(long companyId) {
      Company company = companyRepository.findOne(companyId);
      if(company != null){
         return Optional.of(company);
      }
      return Optional.empty();
   }


   @Override public Page<Company> findAll(int start, int limit) {
      return companyRepository.findAll(new PageRequest(start, limit));
   }


   @Override public Page<Company> findAll(Pageable pageable) {
      return companyRepository.findAll(pageable);
   }

   @Override public Page<CompanyNetStat> findAllNetStats(Pageable pageable) {
      return companyNetStatRepository.findAll(pageable);
   }


   @Override public Page<Company> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir, String companyNameLike) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)){
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(companyNameLike)) {
         return findAll(request);
      } else {
         return companyRepository.findAll(new SingleSpecification<>(new SearchCriteria("name", ":", companyNameLike)), request);
      }
   }

   @Override public Page<CompanyNetStat> findAllNetStats(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillLike) {
      PageRequest request;
      if(StringUtils.isEmpty(sortingField)){
         request = new PageRequest(pageIndex, pageSize);
      } else {
         request = new PageRequest(pageIndex, pageSize, sortingDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortingField);
      }

      if(StringUtils.isEmpty(skillLike)) {
         return findAllNetStats(request);
      } else {
         return companyNetStatRepository.findAll(new SingleSpecification<>(new SearchCriteria("snippet", ":", skillLike)), request);
      }
   }


   @Transactional
   @Override public void clearNetStats() {
      companyNetStatRepository.deleteAll();
   }
}
