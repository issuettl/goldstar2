/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lgekorfrm.sso.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    public static JWTClaimsSet parseToken(String jwtToken) throws Exception {
        JWSObject jwsObject = JWSObject.parse(jwtToken);

        JSONObject jsonPayload = jwsObject.getPayload().toJSONObject();
        return JWTClaimsSet.parse(jsonPayload);
    }

    public static boolean verify(JWSObject jwsObject, RSAPublicKey publicKey) throws Exception {
        JWSHeader header = jwsObject.getHeader();
        JWSAlgorithm algorithm = header.getAlgorithm();

        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        return jwsObject.verify(verifier);
    }

    public static boolean verifyToken(String token, String publicKey) {
        try {
            JWTClaimsSet jwtClaimsSet = parseToken(token);

            JWSObject jwsObject = JWSObject.parse(token);
            JWSHeader header = jwsObject.getHeader();
            JWSAlgorithm algorithm = header.getAlgorithm();

            boolean verify = false;

            //case if RS256 algorithm
            if (JWSAlgorithm.RS256.equals(algorithm)) {
                JWSVerifier verifier = new RSASSAVerifier(loadRS256PublicKey(publicKey));
                verify = jwsObject.verify(verifier);
            }

            //case if HS256 algorithm
            else {
                JWSVerifier verifier = new MACVerifier(publicKey);
                verify = jwsObject.verify(verifier);
            }

            if (!verify) {
                return false;
            }

            //코드의 발급시간을 확인한다.
            Date currentTime = new Date();
            Date expirationTime = jwtClaimsSet.getExpirationTime();
            long diff = (long) Math.floor((expirationTime.getTime() - currentTime.getTime()) / 1000);

            if (diff <= 0) {
                return false;
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static RSAPublicKey loadRS256PublicKey(String publicKey) throws Exception {
        // Base64 decode the data
        byte[] pubdecode = Base64.getDecoder().decode(publicKey);

        /* Generate public key. */
        X509EncodedKeySpec ks = new X509EncodedKeySpec(pubdecode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) kf.generatePublic(ks);
        return rsaPublicKey;
    }

}
