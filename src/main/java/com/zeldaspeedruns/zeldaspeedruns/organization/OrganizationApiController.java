package com.zeldaspeedruns.zeldaspeedruns.organization;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationApiController {
    private final OrganizationService service;

    public OrganizationApiController(OrganizationService service) {
        this.service = service;
    }

    @PostMapping
    public void create(Principal principal) {
        service.saveOrganization(
                Organization.of("ZeldaSpeedRuns", "zeldaspeedruns"),
                UUID.fromString(principal.getName())
        );
    }
}
