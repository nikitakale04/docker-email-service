package com.brightwheel.microservices.emailservice.services;

import com.brightwheel.microservices.emailservice.pojos.Email;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@Service
public class SendGridEmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridEmailServiceImpl.class);

    @Value("${sendgrid.domainName:}") public String domainName;

    @Value("${sendgrid.apiKey:}") public String apiKey;

    public void sendSimpleMessage(Email email) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        LOGGER.info(domainName);
        LOGGER.info(apiKey);
        HttpPost post = new HttpPost(domainName);

        JSONObject json = new JSONObject();
        JSONObject fromJsonObject = new JSONObject();

        ArrayList<JSONObject> contentArray = new ArrayList<>();
        JSONObject contentJsonObject = new JSONObject();
        contentJsonObject.put("type", "text/plain");
        contentJsonObject.put("value", email.body);
        contentArray.add(contentJsonObject);

        ArrayList<JSONObject>personalizationsArray = new ArrayList<>();
        JSONObject personalizationsJsonObject = new JSONObject();

        ArrayList<JSONObject>toArray = new ArrayList<>();
        JSONObject toObject = new JSONObject();
        toObject.put("email", email.to);
        toArray.add(toObject);

        personalizationsJsonObject.put("to", toArray);
        personalizationsArray.add(personalizationsJsonObject);

        fromJsonObject.put("email", email.from);

        json.put("personalizations", personalizationsArray);
        json.put("from", fromJsonObject);
        json.put("subject", email.subject);
        json.put("content", contentArray);


        StringEntity entity = new StringEntity(json.toString());

        post.setEntity(entity);

        post.addHeader("Content-Type", "application/json");

        // this is for sendGrid
        post.addHeader("Authorization", apiKey);

        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) {
                return true;
            }
        });
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        CloseableHttpResponse response = httpclient.execute(post);
        LOGGER.info(String.valueOf(response));

    }
}
