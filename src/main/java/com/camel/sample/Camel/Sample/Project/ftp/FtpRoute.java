package com.camel.sample.Camel.Sample.Project.ftp;

import org.apache.camel.builder.RouteBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpRoute extends RouteBuilder {

    //For controlling route activity from Environment Variables
    @Value("${activate}")
    private Boolean activate;

    @Override
    public void configure() throws Exception {
        from(fromUriBuilder())
                .routeId("firstRoute")
                .precondition("{{activate}}")
                .onException(Exception.class)
                .handled(true)
                .log("WARN: ${exception.message}")
                .log("Transfer Completed ${file:name}")
                .to(toUriBuilder());
    }

    private final String fromUriBuilder() {

        URIBuilder from =  new URIBuilder()
                .setScheme("ftp")
                .setHost("127.0.0.1")
                .setPort(21)
                .setPath("/in_ftp")
                .setUserInfo("beyza")
                .setParameter("connectTimeout", "3000")
                .setParameter("password", "beyza");
        return from.toString();
    }


    private final String toUriBuilder() {

        URIBuilder to = new URIBuilder()
                .setScheme("ftp")
                .setHost("127.0.0.1")
                .setPort(21)
                .setUserInfo("beyza")
                .setPath("/out_ftp")
                .setParameter("connectTimeout", "3000")
                .setParameter("password", "beyza");
        return to.toString();
    }

}