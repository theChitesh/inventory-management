package com.inventory.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.joining;

/**
 * Authorization class helps in retrieving the stored public key for token verification.
 * @author chitesh
 *
 */
public class AuthorizationKey {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationKey.class);

  private static final String CERTIFICATE_BEGIN_STRING = "-----BEGIN CERTIFICATE-----"+System.getProperty("line.separator");
  private static final String CERTIFICATE_END_STRING = "-----END CERTIFICATE-----";
  private static final Map<String, PublicKey> publicKeyMap = new HashMap<>();

  static {
    publicKeyMap.put("ODcxOTBCOUVGOTUxODJDNjkyOUFGQzVGRUU5M0FFOEMyODE2MjgxOA",
        toPublicKey("ODcxOTBCOUVGOTUxODJDNjkyOUFGQzVGRUU5M0FFOEMyODE2MjgxOA").get());
  }

  /**
   * Methods returns the public of the certificate used.
   * @param keyId
   * @return
   */
  private static Optional<PublicKey> toPublicKey(final String keyId) {

    final String certificateString =
        new BufferedReader(new InputStreamReader(AuthorizationKey.class.getClassLoader().getResourceAsStream("inventory_public.crt")))
            .lines().collect(joining(System.getProperty("line.separator")));
    final String certificateCore = certificateString
        .replace(CERTIFICATE_BEGIN_STRING, "")
        .replace(CERTIFICATE_END_STRING, "");

    try {
      final byte[] certificateBytes = Base64.getMimeDecoder().decode(certificateCore);
      final CertificateFactory factory = CertificateFactory.getInstance("X.509");
      
      final X509Certificate x509Certificate =
          (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certificateBytes));
      
      if (x509Certificate == null) {
        LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Could not generate certificate out of '{}'",
            keyId, certificateString);
        return Optional.empty();
      }
      final PublicKey publicKey = x509Certificate.getPublicKey();
      if (publicKey == null) {
        LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Public key not found in certificate: '{}'",
            keyId, certificateString);
        return Optional.empty();
      }
      return Optional.of(publicKey);
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Unable to base64 decode the certificate string: {}.",
          keyId, certificateCore, e);
    } catch (CertificateException e) {
      LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Unable to process the string to RSA certificate.",
          keyId, e);
    }
    return Optional.empty();
  }

  public static Optional<PublicKey> getPublicKey(final String keyId) {
    return Optional.ofNullable(publicKeyMap.get(keyId));
  }
}
