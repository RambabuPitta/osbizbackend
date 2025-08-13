package com.orionsolwings.osbiz.businessConfig.service;

import java.util.List;

import com.orionsolwings.osbiz.businessConfig.model.BranchConfig;

public interface BranchConfigService {
//    BranchConfig createBranch(BranchConfig branchConfig);
    String createBranch(BranchConfig branchConfig);
    String updateBranch(String branchId, BranchConfig branchConfig);
    List<BranchConfig> getAllBranches();
    BranchConfig getBranchById(String branchId);
	boolean deleteBranchById(String branchId);
}