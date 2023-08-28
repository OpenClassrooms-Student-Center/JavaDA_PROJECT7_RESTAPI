package com.nnk.springboot.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormatDate {

    private Date dateFromatDate;
    private String stringFromatDate;
    private Timestamp timestampFromatDate;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(FormatDate.class);

    @Autowired
    private LoggerApi loggerApi;

    public FormatDate() {
    }

    public FormatDate(Date dateFromatDate, String stringFromatDate, Timestamp timestampFromatDate) {
        this.dateFromatDate = dateFromatDate;
        this.stringFromatDate = stringFromatDate;
        this.timestampFromatDate = timestampFromatDate;
    }

    /**
     * @return Date
     */
    public Date getDateFromatDate() {
        return this.dateFromatDate;
    }

    
    /** 
     * @param dateFromatDate
     */
    public void setDateFromatDate(Date dateFromatDate) {
        this.dateFromatDate = dateFromatDate;
    }

    public String getStringFromatDate() {
        return this.stringFromatDate;
    }

    public void setStringFromatDate(String stringFromatDate) {
        this.stringFromatDate = stringFromatDate;
    }

    public Timestamp getTimestampFromatDate() {
        return this.timestampFromatDate;
    }

    public void setTimestampFromatDate(Timestamp timestampFromatDate) {
        this.timestampFromatDate = timestampFromatDate;
    }

    public void setFromatDateStringToTimestamp(String tradeDate) throws ParseException {

        String inDate = tradeDate.replace("T", " ");
        String motifPattern = "yyyy-MM-dd HH:mm";
        DateFormat df = new SimpleDateFormat(motifPattern);
        Date date = df.parse(inDate);
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        timestampFromatDate = ts;

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi
                    .loggerDebug("Set format date to " + motifPattern + " " + timestampFromatDate);
            LOGGER.debug(messageLoggerDebug);
        }

    }

}