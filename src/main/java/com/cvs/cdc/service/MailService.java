package com.cvs.cdc.service;

import com.cvs.cdc.model.CdcMail;
import com.cvs.cdc.model.EmailPayload;

public interface MailService {
    void sendEmail(CdcMail cdcMail, String emailPayload);
}
