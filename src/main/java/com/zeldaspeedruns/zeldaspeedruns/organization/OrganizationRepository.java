package com.zeldaspeedruns.zeldaspeedruns.organization;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, UUID> {
}
