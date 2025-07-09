package com.orionsolwings.osbiz.userManagement.repository;

import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags.PermissionId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionsRepository extends MongoRepository<PermissionFlags, PermissionId> {

    List<PermissionFlags> findByIdUserId(String userId);

    List<PermissionFlags> findByIdRole(String role);

    List<PermissionFlags> findByIdModule(String module);

    PermissionFlags findByIdUserIdAndIdRoleAndIdModule(String userId, String role, String module);

    void deleteByIdUserId(String userId);
}
