/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.caib.dp3t.ibcovid.configbackend.controller.signature;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.util.Base64Utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class SignatureResponseWrapper extends HttpServletResponseWrapper {

    private static final String HEADER_SIGNATURE = "Signature";
    private static final String HEADER_PUBLIC_KEY = "X-Public-Key";
    private static final String HEADER_DIGEST = "Digest";
    private static final String ISSUER_DP3T = "dp3t";
    private static final String CLAIM_HASH_ALG = "hash-alg";
    private static final String CLAIM_CONTENT_HASH = "content-hash";
    // after number of days days the list and hence the signature is invalid
    public final int retentionPeriod;
    private final MessageDigest digest;
    private final ByteArrayOutputStream output;
    private final KeyPair pair;
    private final List<String> protectedHeaders;
    private HashStream stream;
    private PrintWriter writer;

    public SignatureResponseWrapper(final HttpServletResponse response, final KeyPair pair, final int retentionDays,
                                    final List<String> protectedHeaders) {
        super(response);
        this.pair = pair;
        this.protectedHeaders = protectedHeaders;
        try {
            output = new ByteArrayOutputStream(response.getBufferSize());
            digest = MessageDigest.getInstance("SHA-256");
            stream = new HashStream(digest, output);
            retentionPeriod = retentionDays;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (stream == null) {
            stream = new HashStream(digest, output);
        }
        return stream;
    }

    public byte[] getHash() throws IOException {
        return stream.getHash();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (output != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(output, getCharacterEncoding()));
        }

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        setSignature();
        super.flushBuffer();
        if (writer != null) {
            writer.flush();
        } else if (output != null) {
            output.flush();
        }
    }

    public void outputData(final OutputStream httpOutput) throws IOException {
        setSignature();
        httpOutput.write(output.toByteArray());
    }

    private void setSignature() throws IOException {
        final byte[] theHash = getHash();

        final Claims claims = Jwts.claims();
        claims.put(CLAIM_CONTENT_HASH, Base64.getEncoder().encodeToString(theHash));
        claims.put(CLAIM_HASH_ALG, "sha-256");

        claims.setIssuer(ISSUER_DP3T);
        claims.setIssuedAt(Date.from(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).toInstant()));
        claims.setExpiration(Date.from(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).plusDays(retentionPeriod).toInstant()));
        for (final String header : protectedHeaders) {
            if (!containsHeader(header)) {
                continue;
            }

            final String normalizedHeader = header.toLowerCase().replace("x-", "");
            final String headerValue = getHeader(header);
            claims.put(normalizedHeader, headerValue);
            if (normalizedHeader.equals("batch-release-time")) {
                final OffsetDateTime issueDate = OffsetDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(headerValue)), ZoneOffset.UTC);
                claims.setIssuedAt(Date.from(issueDate.toInstant()));
                claims.setExpiration(Date.from(issueDate.plusDays(retentionPeriod).toInstant()));
            }
        }
        final SignatureAlgorithm algorithm = SignatureAlgorithm.ES256;
        final String signature = Jwts.builder().setClaims(claims).signWith(algorithm, pair.getPrivate()).compact();

        setHeader(HEADER_DIGEST, "sha-256=" + Hex.toHexString(theHash));
        setHeader(HEADER_PUBLIC_KEY, getPublicKeyAsPEM());
        setHeader(HEADER_SIGNATURE, signature);

    }

    private String getPublicKeyAsPEM() throws IOException {
        final StringWriter writer = new StringWriter();
        final PemWriter pemWriter = new PemWriter(writer);
        pemWriter.writeObject(new PemObject("PUBLIC KEY", pair.getPublic().getEncoded()));
        pemWriter.flush();
        pemWriter.close();
        return Base64Utils.encodeToUrlSafeString(writer.toString().trim().getBytes());
    }

    private class HashStream extends ServletOutputStream {

        private final MessageDigest digest;
        private final ByteArrayOutputStream output;

        public HashStream(final MessageDigest digest, final ByteArrayOutputStream outputStream) {
            this.digest = digest;
            output = outputStream;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(final WriteListener listener) {

        }

        @Override
        public void write(final int b) throws IOException {
            digest.update((byte) b);
            output.write(b);
        }

        @Override
        public void close() throws IOException {
            output.close();
        }

        public byte[] getHash() throws IOException {
            return digest.digest();
        }

    }

}
