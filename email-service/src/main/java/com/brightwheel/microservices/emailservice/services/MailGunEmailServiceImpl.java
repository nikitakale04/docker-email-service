package com.brightwheel.microservices.emailservice.services;

import com.brightwheel.microservices.emailservice.pojos.Email;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailGunEmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailGunEmailServiceImpl.class);

    @Value("${mailgun.domainName:}") public String domainName;

    @Value("${mailgun.endpoint:}") public String endpoint;

    @Value("${mailgun.baseUrl:}") public String mailgunBaseUrl;

    @Value("${mailgun.apiKey:}") public String apiKey;

    public void sendSimpleMessage(Email email) throws Exception {
        LOGGER.info(domainName);
        LOGGER.info(endpoint);
        LOGGER.info(mailgunBaseUrl);
        LOGGER.info(apiKey);

        HttpPost post = new HttpPost(mailgunBaseUrl + domainName + endpoint);

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("to", email.to));
        urlParameters.add(new BasicNameValuePair("from", email.from_name + " " + email.from));
        urlParameters.add(new BasicNameValuePair("subject", email.subject));
        urlParameters.add(new BasicNameValuePair("text", email.body));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        // this authorisation can be configured
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
