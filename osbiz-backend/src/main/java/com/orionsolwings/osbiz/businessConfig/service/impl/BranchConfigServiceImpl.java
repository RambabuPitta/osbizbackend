package com.orionsolwings.osbiz.businessConfig.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.orionsolwings.osbiz.businessConfig.model.BranchConfig;
import com.orionsolwings.osbiz.businessConfig.repository.BranchConfigRepository;
import com.orionsolwings.osbiz.businessConfig.service.BranchConfigService;

@Service
public class BranchConfigServiceImpl implements BranchConfigService {

    private final BranchConfigRepository repository;

    public BranchConfigServiceImpl(BranchConfigRepository repository) {
        this.repository = repository;
    }

//    @Override
////    public BranchConfig createBranch(BranchConfig branchConfig) {
//        public String createBranch(BranchConfig branchConfig) {
//        try {
//            if (repository.findByBusinessCode(branchConfig.getBusinessCode()) != null) {
//                throw new RuntimeException("Branch with ID " + branchConfig.getBusinessCode() + " already exists.");
//            }
//            return repository.save(branchConfig);
//        } catch (Exception e) {
//        	
//        	return "Branch with ID " + branchConfig.getBusinessCode() + " already exists.";
////            throw new RuntimeException("Error creating branch: " + e.getMessage());
//        }
//    }
    
    @Override
    public String createBranch(BranchConfig branchConfig) {
        if (repository.findByBusinessCode(branchConfig.getBusinessCode()) != null) {
            throw new RuntimeException("Branch with ID " + branchConfig.getBusinessCode() + " already exists.");
        }
        repository.save(branchConfig);
        return branchConfig.getCompanyName() + " created successfully";
    }


    @Override
    public String updateBranch(String businessCode, BranchConfig branchConfig) {
        BranchConfig existing = repository.findByBusinessCode(businessCode);
        if (existing == null) {
            throw new RuntimeException("Branch with ID " + businessCode + " not found.");
        }
        branchConfig.setId(existing.getId());
        repository.save(branchConfig);
        return branchConfig.getCompanyName() + " updated successfully";
    }


    @Override
    public List<BranchConfig> getAllBranches() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching branches: " + e.getMessage());
        }
    }

    @Override
    public BranchConfig getBranchById(String branchId) {
        try {
            BranchConfig branch = repository.findByBusinessCode(branchId);
            if (branch == null) {
                throw new RuntimeException("Branch with ID " + branchId + " not found.");
            }
            return branch;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching branch: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteBranchById(String branchId) {
		return false;}
}
