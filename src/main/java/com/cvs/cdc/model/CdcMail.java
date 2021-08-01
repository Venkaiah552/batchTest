package com.cvs.cdc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CdcMail {
    private String mailFrom;

    private String mailTo;

    private String mailCc;

    private String mailBcc;

    private String mailSubject;

    private String mailContent;
    private String contentType;

    private List<Object> attachments;

    public Date getMailSendDate() {
        return new Date();
    }

    public CdcMail() {
        contentType = "text/plain";
    }
}
