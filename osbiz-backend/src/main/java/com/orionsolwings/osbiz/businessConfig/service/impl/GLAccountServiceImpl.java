package com.orionsolwings.osbiz.businessConfig.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orionsolwings.osbiz.businessConfig.model.GLAccount;
import com.orionsolwings.osbiz.businessConfig.model.NumberRange;
import com.orionsolwings.osbiz.businessConfig.repository.GLAccountRepository;
import com.orionsolwings.osbiz.businessConfig.repository.NumberRangeRepository;
import com.orionsolwings.osbiz.businessConfig.service.GLAccountService;
import com.orionsolwings.osbiz.common.enums.ModuleNumberRange;

@Service
public class GLAccountServiceImpl implements GLAccountService {

    @Autowired
    private GLAccountRepository repository;
    
    @Autowired
    private NumberRangeRepository nrRepository;
    
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
    
//    @Override
//    public GLAccount createGLAccount(GLAccount account) {
//        Date now = new Date();
//        account.setCreatedDate(now);
//
//        try {
//            return repository.save(account);
//        } catch (DuplicateKeyException e) {
//            // Check exception message or error code for specific index failure (optional)
//            String errorMessage = e.getMessage();
//            if (errorMessage != null && errorMessage.contains("unique_businessCode_accountType")) {
//                throw new RuntimeException("GL Account with businessCode '" + account.getBusinessCode() + 
//                    "' and accountType '" + account.getAccountType() + "' already exists.");
//            } else if (errorMessage != null && errorMessage.contains("glAccount")) {
//                throw new RuntimeException("GL Account already exists with glAccount number: " + account.getGlAccount());
//            } else {
//                throw new RuntimeException("Duplicate key error: " + e.getMessage());
//            }
//        }
//    }
    
    @Override
    public GLAccount createGLAccount(GLAccount account) {
        // 1. Find NumberRange for GLACCOUNT
        NumberRange nr = nrRepository.findByModuleType(ModuleNumberRange.GLACCOUNT.name())
            .orElseThrow(() -> new RuntimeException("Number Range not configured for GLACCOUNT"));

        // 2. Validate that range is not exceeded
        if (nr.getCurrentNumber() >= nr.getMaxNumber()) {
            throw new RuntimeException("GL Account number range exceeded for module: GLACCOUNT");
        }

        // 3. Get next number
        Long nextNumber = nr.getCurrentNumber() + 1;

        // 4. Assign it to GLAccount (convert to string if GLAccount.glAccount is String)
        account.setGlAccount(String.valueOf(nextNumber));

        // 5. Set creation metadata
        account.setCreatedDate(new Date());

        // 6. Update NumberRange
        nr.setCurrentNumber(nextNumber);
        nrRepository.save(nr);

        // 7. Save and return GLAccount
        return repository.save(account);
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
//            account.setEntityType(updatedAccount.getEntityType());
            account.setBillPolicy(updatedAccount.getBillPolicy());
            account.setAccountType(updatedAccount.getAccountType());
            account.setStatus(updatedAccount.getStatus());
            return repository.save(account);
        }).orElseThrow(() -> new RuntimeException("GLAccount not found"));
    }

    @Override
    public void deleteGLAccount(String id) {
        repository.deleteById(id);
    }
}
