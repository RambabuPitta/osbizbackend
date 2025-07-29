// Service Interface
package com.orionsolwings.osbiz.businessConfig.service;

import com.orionsolwings.osbiz.businessConfig.model.GLAccount;
import java.util.List;
import java.util.Optional;

public interface GLAccountService {
    GLAccount createGLAccount(GLAccount account);
    Optional<GLAccount> getGLAccountById(String id);
    List<GLAccount> getAllGLAccounts();
    GLAccount updateGLAccount(String id, GLAccount account);
    void deleteGLAccount(String id);
	Optional<GLAccount> getGLAccountByAccNo(String id);
}