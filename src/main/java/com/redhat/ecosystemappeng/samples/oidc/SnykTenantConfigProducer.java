package com.redhat.ecosystemappeng.samples.oidc;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.oidc.OidcTenantConfig;
import io.quarkus.oidc.OidcTenantConfig.ApplicationType;
import io.quarkus.oidc.OidcTenantConfig.Authentication;
import io.quarkus.oidc.common.runtime.OidcCommonConfig;
import io.quarkus.oidc.common.runtime.OidcCommonConfig.Credentials.Secret;
import io.quarkus.oidc.common.runtime.OidcCommonConfig.Credentials.Secret.Method;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Dependent
public class SnykTenantConfigProducer {

  @ConfigProperty(name = "snyk.api.version")
  Optional<String> apiVersion;

  @ConfigProperty(name = "snyk.auth.client_id")
  Optional<String> clientId;

  @ConfigProperty(name = "snyk.auth.client_secret")
  Optional<String> clientSecret;

  @ConfigProperty(name = "snyk.auth.oauth-server-url")
  Optional<String> oauthServer;

  @ConfigProperty(name = "snyk.auth.authorization-path")
  Optional<String> authPath;

  @ConfigProperty(name = "snyk.auth.token-path")
  Optional<String> tokenPath;

  @ConfigProperty(name = "snyk.auth.user-info-path")
  Optional<String> userInfoPath;

  @Produces
  @Singleton
  @Named("snyk")
  public Supplier<OidcTenantConfig> getTenantConfig() {
    var cfg = new OidcTenantConfig();
    if(oauthServer.isEmpty()) {
      return () -> null;
    }
    cfg.setTenantId("snyk");
    cfg.setClientId(clientId.get());
    cfg.setAuthServerUrl(oauthServer.get());
    cfg.setApplicationType(ApplicationType.WEB_APP);
    cfg.setDiscoveryEnabled(Boolean.FALSE);
    cfg.setAuthorizationPath(authPath.get());
    cfg.setTokenPath(tokenPath.get());
    cfg.setUserInfoPath(userInfoPath.get());
    
    var secret = new Secret();
    secret.setMethod(Method.POST);
    secret.setValue(clientSecret.get());
    
    var creds = new OidcCommonConfig.Credentials();
    creds.setClientSecret(secret);
    cfg.setCredentials(creds);

    var auth = new Authentication();
    auth.setIdTokenRequired(Boolean.FALSE);
    auth.setPkceRequired(Boolean.TRUE);
    auth.setExtraParams(Map.of("version", apiVersion.get()));
    cfg.setAuthentication(auth);

    cfg.setUserInfoPath("https://app.snyk.io/api/v1/user/me");
    return () -> cfg;
  }

}
