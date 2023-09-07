package com.lmluat.league.config;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@BasicAuthenticationMechanismDefinition(realmName = "defaultRealm")
@ApplicationScoped
public class AppConfig {

}
