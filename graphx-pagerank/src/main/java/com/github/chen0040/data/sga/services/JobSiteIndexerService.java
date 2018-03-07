package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.JobContract;
import com.github.chen0040.data.commons.models.IndexerQueryPage;
import com.github.chen0040.data.commons.models.IndexerQuery;


/**
 * Created by xschen on 23/10/2016.
 */
public interface JobSiteIndexerService {
   IndexerQueryPage<JobContract> index(IndexerQuery query);
}
