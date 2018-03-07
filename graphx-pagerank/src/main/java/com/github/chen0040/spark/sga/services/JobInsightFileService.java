package com.github.chen0040.spark.sga.services;


import com.github.chen0040.data.commons.models.MyWorkerCompleted;
import com.github.chen0040.data.commons.models.MyWorkerProgress;

import java.io.IOException;
import java.util.function.Consumer;


/**
 * Created by xschen on 9/2/2017.
 */
public interface JobInsightFileService {
   MyWorkerCompleted writeLargeFile(String filename, Consumer<MyWorkerProgress> progressHandler) throws IOException;
}
