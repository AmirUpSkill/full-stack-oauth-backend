package com.amir.backend.controller;

import com.amir.backend.model.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser; // Using OidcUser for standard claims
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user") // Base path for user-related endpoints
public class UserController {

    /**
     * Endpoint to get the currently authenticated user's details.
     * Accessible only after successful OAuth2 login.
     *
     * @param oidcUser Represents the authenticated user, injected by Spring Security.
     *                 Contains standard OIDC claims like email, name, etc.
     * @param authenticationToken Represents the full authentication object, including registration ID.
     * @return UserDTO containing relevant user information.
     */
    @GetMapping("/me") // Maps HTTP GET requests for /api/user/me to this method
    public UserDTO getCurrentUser(@AuthenticationPrincipal OidcUser oidcUser,
                                    OAuth2AuthenticationToken authenticationToken) {

        // Extract user details safely
        // Use Optional to handle potential nulls gracefully, though standard claims should be present
        String name = Optional.ofNullable(oidcUser)
                              .map(OidcUser::getFullName)
                              .orElse("N/A"); // Default value if name is null

        String email = Optional.ofNullable(oidcUser)
                               .map(OidcUser::getEmail)
                               .orElse("N/A"); // Default value if email is null

        String provider = Optional.ofNullable(authenticationToken)
                                  .map(OAuth2AuthenticationToken::getAuthorizedClientRegistrationId)
                                  .orElse("N/A"); // Default value if provider ID is null

        // Build and return the UserDTO using the builder pattern from Lombok
        return UserDTO.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .build();
    }
}