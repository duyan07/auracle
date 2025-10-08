package com.duyan.auracle.auracle_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ClerkJwksKeyResolver extends SigningKeyResolverAdapter{

    private final String jwksUrl;
    private final RestTemplate restTemplate;
    private Map<String, Object> jwksCache;

    public ClerkJwksKeyResolver(String jwksUrl) {
        this.jwksUrl = jwksUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        try {
            // get the "kid" (key ID) from the JWT header
            String keyId = header.getKeyId();

            // fetch JWKS if not cached
            if (jwksCache == null) {
                jwksCache = restTemplate.getForObject(jwksUrl, Map.class);
            }

            // find the matching key from JWKS
            List<Map<String, Object>> keys = (List<Map<String, Object>>) jwksCache.get("keys");
            for (Map<String, Object> key : keys) {
                if (keyId.equals(key.get("kid"))) {
                    return buildPublicKey(key);
                }
            }

            throw new RuntimeException("No matching key found in JWKS");
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve signing key", e);
        }
    }

    public Key buildPublicKey(Map<String, Object> keyData) throws Exception {
        // extract RSA public key components
        String n = (String) keyData.get("n"); // modulus
        String e = (String) keyData.get("e"); // exponent

        // decode Base64URL to bytes
        byte[] nBytes = java.util.Base64.getUrlDecoder().decode(n);
        byte[] eBytes = java.util.Base64.getUrlDecoder().decode(e);

        // build RSA public key
        BigInteger modulus = new BigInteger(1, nBytes);
        BigInteger exponent = new BigInteger(1, eBytes);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }
}
