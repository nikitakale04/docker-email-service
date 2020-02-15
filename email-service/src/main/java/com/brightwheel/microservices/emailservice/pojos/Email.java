package com.brightwheel.microservices.emailservice.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Email {

    @JsonProperty("to")
    public String to;

    @JsonProperty("to_name")
    public String to_name;

    @JsonProperty("from")
    public String from;

    @JsonProperty("from_name")
    public String from_name;

    @JsonProperty("subject")
    public String subject;

    @JsonProperty("body")
    public String body;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(to)
                .append(to_name)
                .append(from)
                .append(from_name)
                .append(subject)
                .append(body)
                .toString();
    }
}
