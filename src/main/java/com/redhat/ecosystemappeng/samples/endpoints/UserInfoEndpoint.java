package com.redhat.ecosystemappeng.samples.endpoints;

import com.redhat.ecosystemappeng.samples.oidc.TokenCache;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/user-info")
public class UserInfoEndpoint {

  private static final String RHDA_USER_HEADER = "RHDA-User";

  @Inject
  AccessTokenCredential accessToken;

  @Inject
  TokenCache tokenCache;

  @Inject
  OidcClient oidcClient;

  @GET
  @Authenticated
  public Response getUserInfo(@QueryParam(RHDA_USER_HEADER) String userId) {
    tokenCache.setToken(userId, accessToken.getToken());
    return Response.ok("AccessToken: " + accessToken.getToken() + "\nRefreshToken: " + accessToken.getRefreshToken().getToken()).build();
  }

}
