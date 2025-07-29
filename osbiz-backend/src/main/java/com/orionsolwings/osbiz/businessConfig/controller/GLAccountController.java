package com.orionsolwings.osbiz.businessConfig.controller;

import com.orionsolwings.osbiz.businessConfig.model.GLAccount;
import com.orionsolwings.osbiz.businessConfig.service.GLAccountService;
import com.orionsolwings.osbiz.util.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/glaccounts")
public class GLAccountController {

    @Autowired
    private GLAccountService service;

    @PostMapping
    public ResponseEntity<ApiResponses<GLAccount>> create(@RequestBody GLAccount account) {
        GLAccount saved = service.createGLAccount(account);
        return ResponseEntity.ok(new ApiResponses<>("GL Account created successfully", "success", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponses<GLAccount>> getById(@PathVariable String id) {
//      Optional<GLAccount> optional = service.getGLAccountById(id);
      Optional<GLAccount> optional = service.getGLAccountByAccNo(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new ApiResponses<>("GL Account found", "success", optional.get()));
        } else {
            return ResponseEntity.status(404).body(new ApiResponses<>("GL Account not found", "fail", null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponses<List<GLAccount>>> getAll() {
        List<GLAccount> list = service.getAllGLAccounts();
        return ResponseEntity.ok(new ApiResponses<>("GL Accounts fetched", "success", list));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponses<GLAccount>> update(@PathVariable String id, @RequestBody GLAccount account) {
        try {
            GLAccount updated = service.updateGLAccount(id, account);
            return ResponseEntity.ok(new ApiResponses<>("GL Account updated", "success", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponses<>(e.getMessage(), "fail", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponses<Void>> delete(@PathVariable String id) {
        try {
            service.deleteGLAccount(id);
            return ResponseEntity.ok(new ApiResponses<>("GL Account deleted", "success", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponses<>(e.getMessage(), "fail", null));
        }
    }
}
