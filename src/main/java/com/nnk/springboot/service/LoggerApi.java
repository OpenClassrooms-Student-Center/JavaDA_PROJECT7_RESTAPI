package com.nnk.springboot.service;

import org.springframework.http.HttpStatus;
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

    /**
     * @param request
     * @param response
     * @param param
     * @return String
     */
    public String loggerInfoController(HttpServletRequest request, HttpServletResponse response, String param) {

        String loginfo = "User name : " + request.getUserPrincipal().getName()
                + "\r\nRequest Method: <["
                + request.getMethod() + "]>" + " " + request.getRequestURI()
                + "\r\nRequest URL: " + ServletUriComponentsBuilder.fromCurrentRequest().toUriString();

        if (System.getProperty(CONFIGFILELOG4J).equals(FILELOG4J2XMLTEST)) {

            loginfo += " @Param/Path: " + request.getParameterMap().keySet() + "=[" + param + "]"
                    + "\r\nResponse Code: " + response.getStatus();

        } else {
            loginfo += "\r\nResponse Code : " + " " + HttpStatus.valueOf(response.getStatus());
        }

        return loginfo;

    }

    /**
     * @param param1
     * @param param2
     * @param param3
     * @return String
     */
    public String loggerStrings(String param1, String param2, String param3) {

        String loginfo = "";

        if (System.getProperty(CONFIGFILELOG4J).equals(FILELOG4J2XMLTEST)) {

            loginfo += "Test : " + param1 + param2 + param3;

        } else {
            loginfo += param1 + param2 + param3;
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
            logdeb = STRINGREQUESTURL + ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
                    + " " + param;
        }

        return logdeb;
    }
}