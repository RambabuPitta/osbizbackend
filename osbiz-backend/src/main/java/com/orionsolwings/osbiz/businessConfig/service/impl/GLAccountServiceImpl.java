package com.orionsolwings.osbiz.businessConfig.service.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orionsolwings.osbiz.businessConfig.model.GLAccount;
import com.orionsolwings.osbiz.businessConfig.repository.GLAccountRepository;
import com.orionsolwings.osbiz.businessConfig.service.GLAccountService;

@Service
public class GLAccountServiceImpl implements GLAccountService {

    @Autowired
    private GLAccountRepository repository;

    @Override
    public GLAccount createGLAccount(GLAccount account) {
        account.setCreatedDate(ZonedDateTime.now());
        account.setUpdatedDate(ZonedDateTime.now());
        return repository.save(account);
    }

    @Override
    public Optional<GLAccount> getGLAccountById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<GLAccount> getAllGLAccounts() {
        return repository.findAll();
    }

    @Override
    public GLAccount updateGLAccount(String id, GLAccount updatedAccount) {
        return repository.findById(id).map(account -> {
            account.setBusinessCode(updatedAccount.getBusinessCode());
            account.setGlAccount(updatedAccount.getGlAccount());
            account.setEntityType(updatedAccount.getEntityType());
            account.setBillPolicy(updatedAccount.getBillPolicy());
            account.setAccountType(updatedAccount.getAccountType());
            account.setStatus(updatedAccount.getStatus());
            account.setUpdatedDate(ZonedDateTime.now());
            return repository.save(account);
        }).orElseThrow(() -> new RuntimeException("GLAccount not found"));
    }

    @Override
    public void deleteGLAccount(String id) {
        repository.deleteById(id);
    }
}
