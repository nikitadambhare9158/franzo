package com.franzoo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Random;
@Component
@Service
public class MailService {

@Autowired
private JavaMailSender javaMailSender;

@Autowired
public MailService(JavaMailSender javaMailSender) {
	this.javaMailSender=javaMailSender;
}

	Random random = new Random();
	int otp = 1000 + random.nextInt(8999);
	

public void sendEmail(User user) throws MailException{
	SimpleMailMessage mail=new SimpleMailMessage();
	mail.setTo(user.getEmail());
	mail.setSubject("Email Verification");
	mail.setText("Your OTP for email verification is:" + otp);
	
	javaMailSender.send(mail);
}


}
