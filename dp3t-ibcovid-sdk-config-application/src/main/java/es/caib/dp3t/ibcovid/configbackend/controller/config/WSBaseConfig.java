/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.caib.dp3t.ibcovid.configbackend.controller.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import es.caib.dp3t.ibcovid.configbackend.controller.filter.ResponseWrapperFilter;
import es.caib.dp3t.ibcovid.configbackend.controller.interceptor.HeaderInjector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public abstract class WSBaseConfig implements SchedulingConfigurer, WebMvcConfigurer {

    final SignatureAlgorithm algorithm = SignatureAlgorithm.ES256;

    @Value("${ws.headers.protected:}")
    List<String> protectedHeaders;

    @Value("${ws.retentiondays: 21}")
    int retentionDays;

    @Value("#{${ws.security.headers: {'X-Content-Type-Options':'nosniff', 'X-Frame-Options':'DENY','X-Xss-Protection':'1; mode=block'}}}")
    Map<String, String> additionalHeaders;


    abstract String getPublicKey();

    abstract String getPrivateKey();

    @Bean
    public ResponseWrapperFilter hashFilter() {
        return new ResponseWrapperFilter(getKeyPair(algorithm), retentionDays, protectedHeaders);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderInjector(additionalHeaders));
    }

    public KeyPair getKeyPair(final SignatureAlgorithm algorithm) {
        Security.addProvider(new BouncyCastleProvider());
        Security.setProperty("crypto.policy", "unlimited");
        return new KeyPair(loadPublicKeyFromString(), loadPrivateKeyFromString());
    }

    private PrivateKey loadPrivateKeyFromString() {
        try {
            final String privateKey = getPrivateKey();
            final Reader reader = new StringReader(privateKey);
            final PemReader readerPem = new PemReader(reader);
            final PemObject obj = readerPem.readPemObject();
            final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(obj.getContent());
            final KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
            return (PrivateKey) kf.generatePrivate(pkcs8KeySpec);
        } catch (final Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    private PublicKey loadPublicKeyFromString() {
        try {
            return CertificateFactory
                    .getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(getPublicKey().getBytes()))
                    .getPublicKey();
        } catch (final Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

}
