package com.tengo.learningplatform.users;

import com.tengo.learningplatform.contracts.ProfileSummarizable;
import com.tengo.learningplatform.domain.StaffPermissionTier;


public class Admin extends Staff implements ProfileSummarizable {

    private int accessLevel;
    private StaffPermissionTier permissionTier;

    public Admin(String name, String surname, String email, int accessLevel) {
        super(name, surname, email);
        this.accessLevel = accessLevel;
        this.permissionTier = StaffPermissionTier.fromAccessLevel(accessLevel);
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
        this.permissionTier = StaffPermissionTier.fromAccessLevel(accessLevel);
    }

    public StaffPermissionTier getPermissionTier() {
        return permissionTier;
    }

    public void setPermissionTier(StaffPermissionTier permissionTier) {
        if (permissionTier != null) {
            this.permissionTier = permissionTier;
            this.accessLevel = permissionTier.getMinLevel();
        }
    }

    @Override
    public String getRoleLabel() {
        return "ADMIN";
    }

    @Override
    public String getProfileSummary() {
        return "Admin " + getContactLabel() + ", tier " + permissionTier.getDisplayName()
                + ", access level " + accessLevel;
    }
}
