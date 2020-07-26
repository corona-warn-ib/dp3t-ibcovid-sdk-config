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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Base64;

@Configuration
@Profile("pre | pro")
public class WSProdConfig extends WSBaseConfig {
    @Value("${vcap.services.ecdsa_cs_prod.credentials.publicKey}")
    public String publicKey;
    @Value("${vcap.services.ecdsa_cs_prod.credentials.privateKey}")
    private String privateKey;

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {

    }

    @Override
    String getPrivateKey() {
        return new String(Base64.getDecoder().decode(privateKey));
    }

    @Override
    String getPublicKey() {
        return new String(Base64.getDecoder().decode(publicKey));
    }

}
