package com.orionsolwings.osbiz.businessConfig.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orionsolwings.osbiz.businessConfig.model.BranchConfig;
import com.orionsolwings.osbiz.businessConfig.service.BranchConfigService;
import com.orionsolwings.osbiz.util.ApiResponses;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/businessConfig")
@Validated
public class BranchConfigController {

	private final BranchConfigService service;
	private static final int BRANCH_LIMIT = 3;

	public BranchConfigController(BranchConfigService service) {
		this.service = service;
	}

	@PostMapping("/createBusiness")
	public ResponseEntity<ApiResponses<String>> createBranch(@Valid @RequestBody BranchConfig branchConfig) {
		try {
			List<BranchConfig> existingBranches = service.getAllBranches();
			if (existingBranches.size() >= BRANCH_LIMIT) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponses<>(
						"Branch maximum limit reached, please reach out to support@orionsolwings.com for further assistance",
						"FAILED", "BRANCH_LIMIT_REACHED", null));
			}
			String message = service.createBranch(branchConfig);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponses<>(message, "SUCCESS", null, null));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponses<>(e.getMessage(), "FAILED", null, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Unexpected error: " + e.getMessage(), "FAILED", null, null));
		}
	}

	@GetMapping("/getBusinessConfig")
	public ResponseEntity<ApiResponses<List<BranchConfig>>> getAllBranches() {
		// You can validate or extract token here if needed
		try {
			List<BranchConfig> branches = service.getAllBranches();
			return ResponseEntity.ok(new ApiResponses<>("Fetched successfully", "SUCCESS", null, branches));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Failed to fetch branches: " + e.getMessage(), "FAILED", null, null));
		}
	}

	@GetMapping("/getBusinessConfig/{branchId}")
	public ResponseEntity<ApiResponses<BranchConfig>> getBranchById(@PathVariable String branchId) {
		try {
			BranchConfig branch = service.getBranchById(branchId);
			return ResponseEntity
					.ok(new ApiResponses<>(branch.getCompanyName() + " fetched successfully", "SUCCESS", null, branch));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponses<>(e.getMessage(), "FAILED", null, null));
		}
	}

	@PutMapping("/updateBusiness/{branchId}")
	public ResponseEntity<ApiResponses<String>> updateBranch(@PathVariable String branchId,
			@Valid @RequestBody BranchConfig branchConfig) {
		try {
			String message = service.updateBranch(branchId, branchConfig);
			return ResponseEntity.ok(new ApiResponses<>(message, "SUCCESS", null, null));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponses<>(e.getMessage(), "FAILED", null, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Unexpected error: " + e.getMessage(), "FAILED", null, null));
		}
	}
}
