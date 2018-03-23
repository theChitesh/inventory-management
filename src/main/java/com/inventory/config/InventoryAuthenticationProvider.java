package com.inventory.config;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Authentication Provider implementation for Inventory Management application. Class helps 
 * verification of Authorization JWT token.
 * 
 * @author chitesh
 *
 */
@Component
public class InventoryAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    InventoryUserAuthentication auth = (InventoryUserAuthentication) authentication;
    verifyTokenSignature(auth.getJwt());
    authentication.setAuthenticated(true);
    return authentication;
  }

  @Override
  public boolean supports(Class<?> arg0) {
    return true;
  }

  /**
   * Method validates the JWT token signature
   * @param jwt
   */
  private void verifyTokenSignature(final SignedJWT jwt) {
    final String kidFromToken = jwt.getHeader().getKeyID();
    final Optional<PublicKey> publicKey = AuthorizationKey.getPublicKey(kidFromToken);

    if (!publicKey.isPresent()) {
      throw new RuntimeException("No public key with id ");
    } else if (!(publicKey.get() instanceof RSAPublicKey)) {
      throw new RuntimeException("Public key with id ");
    }

    try {
      final JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey.get());
      if (!jwt.verify(verifier)) {
        throw new RuntimeException("Signature is not valid.");
      }
    } catch (JOSEException e) {
      throw new RuntimeException("Exception occurred while verifying token signature!");
    }
  }
}
