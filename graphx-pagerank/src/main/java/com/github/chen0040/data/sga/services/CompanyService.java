package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.OneToManyToOneAssociation;
import com.github.chen0040.data.sga.models.Company;
import com.github.chen0040.data.sga.models.CompanyNetStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 3/10/2016.
 */
public interface CompanyService {
   Company save(Company company);
   Optional<Company> deleteById(long companyId);

   Optional<Company> findByCompanyName(String companyName);

   void saveSimilarCompanies(String companyName, List<OneToManyToOneAssociation> collection);

   CompanyNetStat findNetStat(String companyName);
   void saveRank(String companyName, double rank);
   long countNetStats();

   void saveClusterId(String companyName, long clusterId);

   Company saveCompanyByName(String companyName, String link);

   Optional<Company> findByCompanyId(long companyId);

   Page<Company> findAll(int start, int limit);
   Page<Company> findAll(Pageable pageable);
   Page<Company> findAll(int pageIndex, int pageSize, String sortingField, String sortingDir, String companyNameLike);

   Page<CompanyNetStat> findAllNetStats(Pageable pageable);
   Page<CompanyNetStat> findAllNetStats(int pageIndex, int pageSize, String sortingField, String sortingDir, String skillLike);

   void clearNetStats();
}
