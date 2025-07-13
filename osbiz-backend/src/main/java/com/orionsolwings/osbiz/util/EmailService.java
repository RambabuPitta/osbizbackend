package com.orionsolwings.osbiz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.orionsolwings.osbiz.company.model.CompanyProfile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendCompanyProfileEmail(CompanyProfile profile) throws MessagingException {
        if (profile.getEmailAddress() == null || profile.getEmailAddress().isBlank()) {
            logger.warn("Email not sent: No recipient email found in profile.");
            throw new IllegalArgumentException("Recipient email address is missing.");
        }

        logger.info("Preparing to send company profile email to: {}", profile.getEmailAddress());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(profile.getEmailAddress());
        
        logger.info("profile.getEmailAddress()----->"+profile.getEmailAddress());
        
        helper.setSubject("Your Company Profile - OrionSolwings");

        Context context = new Context();
        context.setVariable("companyName", profile.getCompanyName());
        context.setVariable("emailAddress", profile.getEmailAddress());
        context.setVariable("phoneNumber", profile.getPhoneNumber());
        context.setVariable("websiteURL", profile.getWebsiteURL());
        context.setVariable("officeAddress", profile.getOfficeAddress());
        context.setVariable("streetAddress", profile.getStreetAddress());
        context.setVariable("streetAddressLine2", profile.getStreetAddressLine2());
        context.setVariable("city", profile.getCity());
        context.setVariable("state", profile.getState());
        context.setVariable("postal", profile.getPostal());
        context.setVariable("country", profile.getCountry());
        context.setVariable("businessOrganizationType", profile.getBusinessOrganizationType());
        context.setVariable("glAccount", profile.getGlAccount());
        context.setVariable("currency", profile.getCurrency());
        context.setVariable("fiscalYear", profile.getFiscalYear());
        context.setVariable("bankHolderName", profile.getBankHolderName());
        context.setVariable("accountName", profile.getAccountName());
        context.setVariable("bankName", profile.getBankName());
        context.setVariable("bankCode", profile.getBankCode());
        context.setVariable("accountType", profile.getAccountType());
        context.setVariable("upi", profile.getUpi());
        context.setVariable("signupAuth", profile.isSignupAuth());
        context.setVariable("verifiyCompanyProfile", profile.isVerifiyCompanyProfile());
        context.setVariable("adminAuth", profile.isAdminAuth());
        context.setVariable("checkboxConfirmation", profile.isCheckboxConfirmation());
        context.setVariable("createtedDate", profile.getCreatetedDate());
        context.setVariable("roleId", profile.getRoleId());
        context.setVariable("contactPersonInformation", profile.getContactPersonInformation());

        String htmlContent = templateEngine.process("email/company-profile-email.html", context);
        helper.setText(htmlContent, true);

        mailSender.send(message);
        logger.info("Email successfully sent to: {}", profile.getEmailAddress());
    }
}
