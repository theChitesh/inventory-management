package com.inventory.config;

import java.util.Collection;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Inventory User Authentication class helps in setting the context for authenticated users.
 * @author chitesh
 *
 */
public class InventoryUserAuthentication implements Authentication {

  private final InventoryUser inventoryUser;
  private boolean authenticated = false;

  public InventoryUser getInventoryUser() {
    return inventoryUser;
  }

  public SignedJWT getJwt() {
    return jwt;
  }

  private final SignedJWT jwt;

  public InventoryUserAuthentication(final String userName, final String userId,
                                     final Collection<GrantedAuthority> roles, final SignedJWT jwt) {
    this.jwt = jwt;
    this.inventoryUser = new InventoryUser(userName, userId, roles);
  }

  @Override
  public String getName() {
    return this.inventoryUser.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return inventoryUser.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.inventoryUser;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
    authenticated = arg0;
  }

}
