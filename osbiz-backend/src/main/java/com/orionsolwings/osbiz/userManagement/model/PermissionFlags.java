package com.orionsolwings.osbiz.userManagement.model;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "permissions")
public class PermissionFlags {

    @Id
    private PermissionId id;

    private List<String> permissions;

    // Getters and Setters
    public PermissionId getId() {
        return id;
    }
    public void setId(PermissionId id) {
        this.id = id;
    }

    public List<String> getPermissions() {
        return permissions;
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    // ==== Embedded ID Class ====
    public static class PermissionId {
        private String userId;
        private String role;
        private String module;
        private String email;  // ✅ added email field inside ID

        public PermissionId() {}

        public PermissionId(String userId, String role, String module, String email) {
            this.userId = userId;
            this.role = role;
            this.module = module;
            this.email = email;
        }

        // Getters and setters
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

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PermissionId)) return false;
            PermissionId that = (PermissionId) o;
            return Objects.equals(userId, that.userId) &&
                   Objects.equals(role, that.role) &&
                   Objects.equals(module, that.module) &&
                   Objects.equals(email, that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, role, module, email);
        }
    }
}
