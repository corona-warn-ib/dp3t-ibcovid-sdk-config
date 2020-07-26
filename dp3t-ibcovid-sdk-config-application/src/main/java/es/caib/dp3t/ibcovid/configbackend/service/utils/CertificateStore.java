package es.caib.dp3t.ibcovid.configbackend.service.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import es.caib.dp3t.ibcovid.configbackend.common.config.AppProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Component
public class CertificateStore {

    private final AppProperties codeGenProperties;

    public CertificateStore(final AppProperties codeGenProperties) {
        this.codeGenProperties = codeGenProperties;
    }

    public PublicKey getPublicKey() {
        try {
            final String publicKeyBase64 = loadPublicKey();
            final byte[] encodedKey = DatatypeConverter.parseBase64Binary(publicKeyBase64);
            final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(keySpec);
        } catch (final NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String loadPublicKey() throws IOException {
        return loadKey(codeGenProperties.getPublicKey());

    }

    private String loadKey(final String keyLocation) throws IOException {
        if (StringUtils.startsWith(keyLocation, "classpath:/")) {
            final InputStream inputStream = new ClassPathResource(keyLocation.substring(11)).getInputStream();
            return readAsStringFromInputStreamAndClose(inputStream);
        } else if (StringUtils.startsWith(keyLocation, "file:/")) {
            final InputStream inputStream = new FileInputStream(keyLocation);
            return readAsStringFromInputStreamAndClose(inputStream);
        }

        return keyLocation;
    }

    private String readAsStringFromInputStreamAndClose(final InputStream input) throws IOException {
        try (input) {
            return IOUtils.toString(input, StandardCharsets.UTF_8);
        }
    }

}
