package com.github.chen0040.data.sga.specs;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 5/2/2017.
 */
public class SpecificationBuilder<T> { 

   private final List<SearchCriteria> params;

   public SpecificationBuilder() {
      params = new ArrayList<SearchCriteria>();
   }

   public SpecificationBuilder<T> with(String key, String operation, Object value) {
      params.add(new SearchCriteria(key, operation, value));
      return this;
   }

   public SpecificationBuilder(List<SearchCriteria> criterias){
      params = criterias;
   }

   public Specification<T> build() {
      if (params.size() == 0) {
         return null;
      }

      List<Specification<T>> specs = new ArrayList<>();
      for (SearchCriteria param : params) {
         specs.add(new SingleSpecification<>(param));
      }

      Specification<T> result = specs.get(0);
      for (int i = 1; i < specs.size(); i++) {
         result = Specifications.where(result).and(specs.get(i));
      }
      return result;
   }
}
