package com.redhat.ecosystemappeng.samples.oidc;

import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenCache {
  
  private final Map<String, String> cache = new HashMap<>();

  public String getToken(String key) {
    return cache.get(key);
  }

  public void setToken(String key, String token) {
    cache.put(key, token);
  }

  public String deleteToken(String key) {
    return cache.remove(key);
  }

  public boolean hasToken(String key) {
    return cache.containsKey(key);
  }
}
