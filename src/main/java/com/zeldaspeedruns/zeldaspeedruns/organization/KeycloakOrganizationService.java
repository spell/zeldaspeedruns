package com.zeldaspeedruns.zeldaspeedruns.organization;

import com.zeldaspeedruns.zeldaspeedruns.user.KeycloakUserService;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.UUID;

@Service
public class KeycloakOrganizationService implements OrganizationService {
    private final RealmResource realm;
    private final OrganizationRepository repository;
    private final KeycloakUserService userService;

    private final static String ORGANIZATION_GROUP_PREFIX = "org:";

    public KeycloakOrganizationService(Keycloak keycloak, @Value("${keycloak.realm}") String realm,
                                       KeycloakUserService userService,
                                       OrganizationRepository repository) {
        this.realm = keycloak.realm(realm);
        this.userService = userService;
        this.repository = repository;
    }

    private boolean organizationGroupExists(String groupName) {
        var result = realm.groups().count(groupName);
        if (result.containsKey("count")) {
            return result.get("count") != 0;
        }
        return false;
    }

    @Override
    @Transactional
    public void saveOrganization(Organization organization, UUID ownerId) {
        organization = repository.save(organization);

        // First, we must check if a Group exists.
        var groupName = ORGANIZATION_GROUP_PREFIX + organization.slug();
        if (organizationGroupExists(groupName)) {
            // TODO: Implement update logic.
            throw new DataIntegrityViolationException("group already exists");
        } else {
            var representation = new GroupRepresentation();
            representation.setName(groupName);

            var response = realm.groups().add(representation);
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new DataIntegrityViolationException(response.toString());
            }

            // Finally, add the user
            var groupId = CreatedResponseUtil.getCreatedId(response);
            var user = userService.getUserResource(ownerId);
            user.joinGroup(groupId);
        }
    }
}
