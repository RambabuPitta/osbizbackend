package com.orionsolwings.osbiz.businessConfig.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.orionsolwings.osbiz.businessConfig.model.NumberRange;
import java.util.Optional;

public interface NumberRangeRepository extends MongoRepository<NumberRange, String> {
    Optional<NumberRange> findByModuleType(String moduleType);
    void deleteByModuleType(String moduleType);
}