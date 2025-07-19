package com.orionsolwings.osbiz.util;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    @Autowired
    private EmailService emailService;

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, otp);

        logger.info("Generated OTP [{}] for email: {}", otp, email);

        sendOtpEmail(email, otp);
        return otp;
    }

    private void sendOtpEmail(String email, String otp) {
        String subject = "Your OTP Code";
        String message = "<p>Your OTP is: <b>" + otp + "</b>. It is valid for 5 minutes.</p>";

        try {
            emailService.sendEmail(email, subject, message);
            logger.info("OTP email sent to {}", email);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to {}: {}", email, e.getMessage());
        }
    }

    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = otpStore.get(email);

        if (storedOtp != null && storedOtp.equals(inputOtp)) {
            otpStore.remove(email);
            logger.info("OTP verified successfully for email: {}", email);
            return true;
        }

        logger.warn("OTP verification failed for email: {} with OTP: {}", email, inputOtp);
        return false;
    }
    
    
}
