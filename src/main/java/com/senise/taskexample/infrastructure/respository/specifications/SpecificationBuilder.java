package com.senise.taskexample.infrastructure.respository.specifications;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationBuilder {

    public static <T> Specification<T> containsIgnoreCase(String fieldName, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
    }

    public static <T> Specification<T> equals(String fieldName, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), value);
    }
}

