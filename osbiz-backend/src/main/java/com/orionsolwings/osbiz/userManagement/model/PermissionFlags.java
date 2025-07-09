package com.orionsolwings.osbiz.userManagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "permissions")
public class PermissionFlags {

    @Id
    private PermissionId id;

    private boolean create;
    private boolean update;
    private boolean read;
    private boolean delete;

    // Getters and Setters
    public PermissionId getId() {
        return id;
    }

    public void setId(PermissionId id) {
        this.id = id;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    // Static embedded ID class
    public static class PermissionId {
        private String userId;
        private String role;
        private String module;

        public PermissionId() {}

        public PermissionId(String userId, String role, String module) {
            this.userId = userId;
            this.role = role;
            this.module = module;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PermissionId)) return false;
            PermissionId that = (PermissionId) o;
            return Objects.equals(userId, that.userId) &&
                   Objects.equals(role, that.role) &&
                   Objects.equals(module, that.module);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, role, module);
        }
    }
}
