package com.orionsolwings.osbiz.businessConfig.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.orionsolwings.osbiz.businessConfig.model.BranchConfig;

@Repository
public interface BranchConfigRepository extends MongoRepository<BranchConfig, String> {

    BranchConfig findByBusinessCode(String brancbusinessCode);
    
}
