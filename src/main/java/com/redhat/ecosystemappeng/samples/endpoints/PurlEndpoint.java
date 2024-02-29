package com.redhat.ecosystemappeng.samples.endpoints;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.redhat.ecosystemappeng.samples.model.PurlRequest;
import com.redhat.ecosystemappeng.samples.restclients.SnykRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/purls")
public class PurlEndpoint {

  @RestClient
  SnykRestClient snykClient;

  @ConfigProperty(name = "snyk.api.orgId")
  String orgId;

  @POST
  public Response getIssues(PurlRequest req) {
    return Response.ok(snykClient.getIssues(orgId, URLEncoder.encode(req.purl(), StandardCharsets.UTF_8))).build();
  }

  @GET
  public Response getIssue() {
    return Response.ok(snykClient.getIssues(orgId, URLEncoder.encode("pkg:maven/commons-fileupload/commons-fileupload@1.3", StandardCharsets.UTF_8))).build();
  }

}
