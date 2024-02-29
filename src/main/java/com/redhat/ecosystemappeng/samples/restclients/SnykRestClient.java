package com.redhat.ecosystemappeng.samples.restclients;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.Encoded;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/")
@ClientQueryParam(name = "version", value = "${snyk.api.version}")
@RegisterRestClient(configKey = "snyk-api")
@OidcClientFilter("snyk-token-filter")
@RegisterClientHeaders
public interface SnykRestClient {

  @GET
  @Path("/orgs/{org_id}/packages/{purl}/issues")
  @Produces("application/vnd.api+json")
  @Encoded
  String getIssues(@PathParam("org_id") String orgId, @PathParam("purl") String purl);

}
