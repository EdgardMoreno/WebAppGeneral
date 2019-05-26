/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.util.beans;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author emoreno
 */
public class SendEmail {
    

    public void sendMailAttachFile(String recipients, String subject, String messageBody, File attachFile) throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", Constantes.CONS_DES_EMAIL_REMITENTE);
        props.put("mail.smtp.clave", Constantes.CONS_DES_EMAIL_REMITENTE_PWD);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(Constantes.CONS_DES_EMAIL_REMITENTE));
            
            String recipient[]  = recipients.split(";");
            for (int i=0; i < recipient.length; i++){
                if (recipient[i].trim().length() > 0)
                    message.addRecipients(Message.RecipientType.TO, recipient[i]);   //Se podrían añadir varios de la misma manera
            }
            
            message.setSubject(subject);
            message.setText(messageBody);


            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            String fileName = attachFile.getName();
            DataSource source = new FileDataSource(attachFile);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", Constantes.CONS_DES_EMAIL_REMITENTE, Constantes.CONS_DES_EMAIL_REMITENTE_PWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            throw new MessagingException(me.getMessage());
        }
    }
    
    public void sendMailSimple(String destinatario, String subject, String messageBody) throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", Constantes.CONS_DES_EMAIL_REMITENTE);
        props.put("mail.smtp.clave", Constantes.CONS_DES_EMAIL_REMITENTE_PWD);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            
            message.setFrom(new InternetAddress(Constantes.CONS_DES_EMAIL_REMITENTE));
            
            String recipient[]  = destinatario.split(";");
            for (int i=0; i < recipient.length; i++){
                if (recipient[i].trim().length() > 0)
                    message.addRecipients(Message.RecipientType.TO, recipient[i]);   //Se podrían añadir varios de la misma manera
            }
            
            message.setSubject(subject);
            //message.setText(cuerpo);
            message.setContent(messageBody, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", Constantes.CONS_DES_EMAIL_REMITENTE, Constantes.CONS_DES_EMAIL_REMITENTE_PWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            throw new MessagingException(me.getMessage());
        }
    }
    
    public void sendMailAttachFile_2(String recipients, String subject, String messageBody, File attachFile) throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", Constantes.CONS_DES_EMAIL_REMITENTE);
        props.put("mail.smtp.clave", "emp.deco");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(Constantes.CONS_DES_EMAIL_REMITENTE));
            
            String recipient[]  = recipients.split(";");
            for (int i=0; i < recipient.length; i++){
                if (recipient[i].trim().length() > 0)
                    message.addRecipients(Message.RecipientType.TO, recipient[i]);   //Se podrían añadir varios de la misma manera
            }
            
            message.setSubject(subject);
            message.setText(messageBody);


            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            String fileName = "ReporteDiario.xlsx";
            DataSource source = new FileDataSource(attachFile);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", Constantes.CONS_DES_EMAIL_REMITENTE, Constantes.CONS_DES_EMAIL_REMITENTE_PWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            throw new MessagingException(me.getMessage());
        }
    }

    
}
