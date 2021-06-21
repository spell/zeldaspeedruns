package com.zeldaspeedruns.zeldaspeedruns.organization;

import java.util.UUID;

public interface OrganizationService {
    void saveOrganization(Organization organization, UUID ownerId);
}
