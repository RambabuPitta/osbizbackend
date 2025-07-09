package com.orionsolwings.osbiz.userManagement.dto;

import com.orionsolwings.osbiz.userManagement.model.User;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;

import java.util.List;

public class UserPermissionRequest {

    private User user;

    private List<PermissionFlags> permissions;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PermissionFlags> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionFlags> permissions) {
        this.permissions = permissions;
    }
}
