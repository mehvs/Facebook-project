package com.example.facebook.service.implementation;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendEmailService {
    public void sendCustomEmail(String emailSubject, String emailReceiver, String message) throws IOException {
        Email from = new Email("vdvasilev04@abv.bg");
        String subject = emailSubject;
        Email to = new Email(emailReceiver);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
