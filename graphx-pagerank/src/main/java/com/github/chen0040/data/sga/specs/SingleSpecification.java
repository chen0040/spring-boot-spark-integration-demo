package com.github.chen0040.data.sga.specs;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Created by xschen on 5/2/2017.
 */
public class SingleSpecification<T> implements Specification<T> {

   private SearchCriteria criteria;

   public SingleSpecification(SearchCriteria criteria) {
      this.criteria = criteria;
   }

   @Override
   public Predicate toPredicate
           (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      if (criteria.getOperation().equalsIgnoreCase(">")) {
         return builder.greaterThanOrEqualTo(
                 root.<String> get(criteria.getKey()), criteria.getValue().toString());
      }
      else if (criteria.getOperation().equalsIgnoreCase("<")) {
         return builder.lessThanOrEqualTo(
                 root.<String> get(criteria.getKey()), criteria.getValue().toString());
      }
      else if (criteria.getOperation().equalsIgnoreCase(":")) {
         if (root.get(criteria.getKey()).getJavaType() == String.class) {
            return builder.like(
                    root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
         } else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
         }
      }
      else if (criteria.getOperation().equalsIgnoreCase("!:")) {
         if (root.get(criteria.getKey()).getJavaType() == String.class) {
            return builder.notLike(
                    root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
         } else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
         }
      }
      return null;
   }
}
