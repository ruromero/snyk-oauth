package com.redhat.ecosystemappeng.samples.oidc;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Named("snyk-token-filter")
public class SnykTokenRequestFilter implements ClientRequestFilter {
  
  private static final String RHDA_USER_HEADER = "RHDA-User";

  @Inject
  TokenCache cache;

  @Override
  public void filter(ClientRequestContext requestContext) throws IOException {
    var user = requestContext.getHeaderString(RHDA_USER_HEADER);
    if(user != null) {
      requestContext.getHeaders().remove(RHDA_USER_HEADER);
      var token = cache.getToken(user);
      if(token != null) {
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
      }
    }
  }
  
}
