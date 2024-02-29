package com.redhat.ecosystemappeng.samples.oidc;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomTenantResolver implements TenantResolver {

  @Override
  public String resolve(RoutingContext context) {
    var provider = context.queryParam("provider");
    if(provider == null || provider.isEmpty()) {
      return null;
    }
    return provider.get(0);
  }
  
}
