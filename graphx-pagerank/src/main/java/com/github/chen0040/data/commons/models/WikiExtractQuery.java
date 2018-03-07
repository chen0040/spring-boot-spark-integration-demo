package com.github.chen0040.data.commons.models;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 16/10/2016.
 */
@Getter
@Setter
public class WikiExtractQuery implements Serializable {
   private static final long serialVersionUID = 7801324472873699116L;

   private List<WikiExtractQueryNormalization> normalized = new ArrayList<>();
   private Map<String, WikiExtractQueryPage> pages = new HashMap<>();
}
