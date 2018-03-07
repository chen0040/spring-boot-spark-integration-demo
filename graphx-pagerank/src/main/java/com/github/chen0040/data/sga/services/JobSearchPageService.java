package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.JobContract;
import com.github.chen0040.data.commons.models.IndexerQueryPage;
import com.github.chen0040.data.commons.models.IndexerQuery;

import java.io.Serializable;


/**
 * Created by xschen on 3/10/2016.
 */
public interface JobSearchPageService extends Serializable {


   IndexerQueryPage<JobContract> crawl(IndexerQuery query);

   IndexerQueryPage<JobContract> translate(IndexerQuery indexerQuery, String json);
}
