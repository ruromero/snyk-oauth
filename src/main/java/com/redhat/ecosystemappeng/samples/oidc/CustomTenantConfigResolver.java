package com.redhat.ecosystemappeng.samples.oidc;

import java.util.function.Supplier;

import io.quarkus.oidc.OidcRequestContext;
import io.quarkus.oidc.OidcTenantConfig;
import io.quarkus.oidc.TenantConfigResolver;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class CustomTenantConfigResolver implements TenantConfigResolver {

  @Inject
  @Named("snyk")
  Supplier<OidcTenantConfig> snykTenantConfig;

  @Override
  public Uni<OidcTenantConfig> resolve(RoutingContext context,
      OidcRequestContext<OidcTenantConfig> requestContext) {
    var tenantId = context.get("tenant-id", "");
    if (!tenantId.isBlank()) {
      return resolveTenant(tenantId);
    }
    var providers = context.queryParam("provider");
    if (providers != null && !providers.isEmpty()) {
      return resolveTenant(providers.get(0));
    }
    return Uni.createFrom().nullItem();

  }

  private Uni<OidcTenantConfig> resolveTenant(String tenantId) {
    switch (tenantId) {
      case "snyk":
        return Uni.createFrom().item(snykTenantConfig);

      default:
        return Uni.createFrom().nullItem();
    }
  }

}
