package com.nnk.springboot.service;

import java.util.Enumeration;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoggerApi {

    private static final String FILELOG4J2XML = "log4j2-spring.xml";
    private static final String FILELOG4J2XMLTEST = "log4j2-spring-test.xml";
    private static final String CONFIGFILELOG4J = "log4j.configurationFile";
    private static final String STRINGREQUESTURL = "Request URL: ";

    public void setLoggerForTests() {
        System.setProperty(CONFIGFILELOG4J, FILELOG4J2XMLTEST);
    }

    public static void setLogger() {
        System.setProperty(CONFIGFILELOG4J, FILELOG4J2XML);
    }

    public String loggerInfo(HttpServletRequest request, HttpServletResponse response, String param) {

        String loginfo = "\r\nRequest Method: <[" + request.getMethod() + "]>" + " " + request.getRequestURI()
                + "\r\nRequest URL: " + ServletUriComponentsBuilder.fromCurrentRequest().toUriString();

        if (System.getProperty(CONFIGFILELOG4J).equals(FILELOG4J2XMLTEST)) {

            loginfo += " @Param/Path: " + request.getParameterMap().keySet() + "=[" + param + "]"
                    + "\r\nResponse Code: " + response.getStatus();

        } else {
            Enumeration<?> entetesheaders = request.getHeaderNames();

            StringBuilder bldentetesheaders = new StringBuilder();
            bldentetesheaders.append("{");
            while (entetesheaders.hasMoreElements()) {
                String nomEntete = (String) entetesheaders.nextElement();
                bldentetesheaders.append("\"" + nomEntete + "\": \"" + request.getHeader(nomEntete) + "\"");
                if (entetesheaders.hasMoreElements()) {
                    bldentetesheaders.append(", ");
                }
            }
            bldentetesheaders.append("}");

            loginfo += "\r\nContent Type: " + request.getContentType() + "\r\nResponse Code: " + response.getStatus()
                    + "\r\nHeader Name: " + bldentetesheaders;
        }

        return loginfo;

    }

    public String loggerErr(Exception e, String param) {

        String logerr = "";

        if (System.getProperty(CONFIGFILELOG4J).equals(FILELOG4J2XMLTEST)) {
            logerr = STRINGREQUESTURL + ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
                    + " @Param/Path: [" + param + "]";
        } else {
            logerr = "\r\nRequest URL: " + ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
                    + "\r\nException: " + e;
        }

        return logerr;

    }

    public String loggerDebug(String param) {

        String logdeb = "";

        if (System.getProperty(CONFIGFILELOG4J).equals(FILELOG4J2XMLTEST)) {
            logdeb = STRINGREQUESTURL + ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
                    + " @Param/Path: [" + param + "]";
        } else {
            logdeb = STRINGREQUESTURL + ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        }

        return logdeb;
    }
}