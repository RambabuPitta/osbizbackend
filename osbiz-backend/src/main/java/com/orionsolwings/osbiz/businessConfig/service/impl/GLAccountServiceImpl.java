package com.orionsolwings.osbiz.businessConfig.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.dao.DuplicateKeyException;
import com.orionsolwings.osbiz.businessConfig.model.GLAccount;
import com.orionsolwings.osbiz.businessConfig.repository.GLAccountRepository;
import com.orionsolwings.osbiz.businessConfig.service.GLAccountService;

@Service
public class GLAccountServiceImpl implements GLAccountService {

    @Autowired
    private GLAccountRepository repository;
    
    Date now ;

//    @Override
//    public GLAccount createGLAccount(GLAccount account) {
//        Date now = new Date();
//        account.setCreatedDate(now);
//
//        try {
//            return repository.save(account);
//        } catch (DuplicateKeyException e) {
//            throw new RuntimeException("GL Account already exists with this glAccount number");
//        }
//    }
    
    @Override
    public GLAccount createGLAccount(GLAccount account) {
        Date now = new Date();
        account.setCreatedDate(now);

        try {
            return repository.save(account);
        } catch (DuplicateKeyException e) {
            // Check exception message or error code for specific index failure (optional)
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("unique_businessCode_accountType")) {
                throw new RuntimeException("GL Account with businessCode '" + account.getBusinessCode() + 
                    "' and accountType '" + account.getAccountType() + "' already exists.");
            } else if (errorMessage != null && errorMessage.contains("glAccount")) {
                throw new RuntimeException("GL Account already exists with glAccount number: " + account.getGlAccount());
            } else {
                throw new RuntimeException("Duplicate key error: " + e.getMessage());
            }
        }
    }


    @Override
    public Optional<GLAccount> getGLAccountById(String id) {
        return repository.findById(id);
    }
    
    @Override
    public Optional<GLAccount> getGLAccountByAccNo(String id) {
        return repository.findByGlAccount(id);
    }

    @Override
    public List<GLAccount> getAllGLAccounts() {
        return repository.findAll();
    }

    @Override
    public GLAccount updateGLAccount(String id, GLAccount updatedAccount) {
    	
    	now = new Date();
        return repository.findById(id).map(account -> {
            account.setBusinessCode(updatedAccount.getBusinessCode());
            account.setGlAccount(updatedAccount.getGlAccount());
            account.setEntityType(updatedAccount.getEntityType());
            account.setBillPolicy(updatedAccount.getBillPolicy());
            account.setAccountType(updatedAccount.getAccountType());
            return repository.save(account);
        }).orElseThrow(() -> new RuntimeException("GLAccount not found"));
    }

    @Override
    public void deleteGLAccount(String id) {
        repository.deleteById(id);
    }
}
