// CompanyProfileRequest.java
package com.orionsolwings.osbiz.company.dto;

import com.orionsolwings.osbiz.company.model.CompanyProfile;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;

import java.util.List;

public class CompanyProfileRequest {

    private CompanyProfile companyProfile;
    private List<PermissionFlags> permissions;

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public List<PermissionFlags> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionFlags> permissions) {
        this.permissions = permissions;
    }
}
