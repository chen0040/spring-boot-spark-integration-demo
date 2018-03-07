package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.JobContract;
import com.github.chen0040.data.commons.models.IndexerQueryPage;
import com.github.chen0040.data.commons.models.IndexerQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 23/10/2016.
 */
public class JobSiteIndexerServiceImpl implements JobSiteIndexerService {
   private JobSearchPageService jobSearchPageService;
   private JobDetailPageService jobDetailPageService;
   private static final Logger logger = LoggerFactory.getLogger(JobSiteIndexerServiceImpl.class);


   public JobSiteIndexerServiceImpl(JobSearchPageService jobSearchPageService, JobDetailPageService jobDetailPageService){
      this.jobSearchPageService = jobSearchPageService;
      this.jobDetailPageService = jobDetailPageService;
   }


   @Override public IndexerQueryPage<JobContract> index(IndexerQuery query) {

      logger.info("indexing: {} ({}) start: {} pageSize: {}", query.getKeywords(), query.getCountry(), query.getStartIndex(), query.getPageSize());

      IndexerQueryPage<JobContract> page = jobSearchPageService.crawl(query);

      List<JobContract> jobs = page.getContent();
      List<JobContract> transformedJobs = new ArrayList<>();
      for(JobContract job : jobs) {
         JobContract transformedJob = jobDetailPageService.addSummary(job);
         transformedJobs.add(transformedJob);
      }
      page.setContent(jobs);

      return page;

   }


}
