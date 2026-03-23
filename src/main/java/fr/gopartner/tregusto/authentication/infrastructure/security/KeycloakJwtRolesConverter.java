package fr.gopartner.tregusto.authentication.infrastructure.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String PREFIX_RESOURCE_ROLE = "ROLE_";
    private static final String CLAIM_ROLES = "roles";


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Collection<String> roles = jwt.getClaim(CLAIM_ROLES);
        if (roles != null && !roles.isEmpty()) {
            Collection<GrantedAuthority> appRoles = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(PREFIX_RESOURCE_ROLE + role))
                    .collect(Collectors.toList());
            grantedAuthorities.addAll(appRoles);
        }


        return grantedAuthorities;
    }
}
