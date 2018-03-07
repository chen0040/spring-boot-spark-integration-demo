package com.github.chen0040.data.sga.services;


import com.github.chen0040.data.commons.models.JobContract;

import java.util.Optional;


/**
 * Created by xschen on 22/10/2016.
 */
public interface JobDetailPageService {
   Optional<String> getSummary(String url);

   JobContract addSummary(JobContract jobContract);
}
