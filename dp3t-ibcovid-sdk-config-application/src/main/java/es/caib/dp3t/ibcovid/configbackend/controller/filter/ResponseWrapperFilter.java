/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.caib.dp3t.ibcovid.configbackend.controller.filter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import es.caib.dp3t.ibcovid.configbackend.controller.signature.SignatureResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
import java.util.List;

public class ResponseWrapperFilter implements Filter {

    private final KeyPair pair;
    private final int retentionDays;
    private final List<String> protectedHeaders;

    public ResponseWrapperFilter(final KeyPair pair, final int retentionDays, final List<String> protectedHeaders) {
        Security.addProvider(new BouncyCastleProvider());
        Security.setProperty("crypto.policy", "unlimited");
        this.pair = pair;
        this.retentionDays = retentionDays;
        this.protectedHeaders = protectedHeaders;
    }

    public PublicKey getPublicKey() {
        return pair.getPublic();
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final SignatureResponseWrapper wrapper = new SignatureResponseWrapper(httpResponse, pair, retentionDays, protectedHeaders);
        chain.doFilter(request, wrapper);
        wrapper.outputData(httpResponse.getOutputStream());
    }

}
