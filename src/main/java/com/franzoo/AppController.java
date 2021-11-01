package com.franzoo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {
	@Autowired
	private UserRepository repo;
	@Autowired
	private MailService mailService;
	
	     
	
//*******************Finding all the Users from Database***************//
		@GetMapping("/process_register")
			public List<User> GetRegistration(@RequestBody User user) {
			return repo.findAll();
		}
		
		@RequestMapping("/")
		public String getSignUpPage() {
			return "SignUp";	
		}
		@RequestMapping("/SignUp_OTP")
		public String getSignUpOTPPage() {
			return "SignUp_OTP";	
		}
//*******************SignUp********************//	
	@RequestMapping(value = "/process_register", method = RequestMethod.POST )
		public ResponseEntity <String> processRegistration(@RequestBody User user) {
		Date dt=  Calendar.getInstance().getTime();
		user.setCreated_at(dt.getTime()+"");
		
		 try {
			 String passwd= user.getPassword();
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 byte[] messageDigest = md.digest(passwd.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 String hashtext= number.toString(16);
			 while(hashtext.length()< 32)
			 {
			 hashtext = "0" + hashtext;
			 }
			 System.out.println("Enc is:"+hashtext);
			 user.setPassword(hashtext);
			 repo.save(user);
			 //****************************************//
				mailService.sendEmail(user);
				String s = String.valueOf(mailService.otp);
				user.setOTP(s);
				System.out.print("harsh");
				repo.save(user);
				System.out.print("harsh");
				//return "register saved successfully";
				//return "/SignUp_OTP";
				return new ResponseEntity<>("Success",HttpStatus.OK);
				}catch(Exception ex) {
					return new ResponseEntity<>("Failed",HttpStatus.NOT_FOUND);
					
				//return "failed:"+ex.getMessage();
				}	
		}
	
	//*********************Otp Authentication After Sign Up*******************//
		@PostMapping("/otpauth")
		public ResponseEntity <String> authenticateUser(@RequestBody OtpAuth otpauth){
			User o= repo.findByOtp(otpauth.getuOtp());
			if(o!=null)
			{
			return new ResponseEntity<>("OTP Verified, You can Log-In",HttpStatus.OK);
			}else
			{
			return new ResponseEntity<>("OTP Verification Failed",HttpStatus.NOT_FOUND);
			}
		}
		
		@DeleteMapping("/delete_user/{uid}")
		public String deleteUser(@PathVariable("uid") Long uid) {
			repo.deleteById(uid);
			return "user deleted";
		}
	
	
	
	

//****************Sign In With Encrypted Password*****************//
	@PostMapping("/signin")
	public ResponseEntity <String> authenticateUser(@RequestBody UserSignIn usersign){
		String passwd=usersign.getPassword();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(passwd.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 String hashtext= number.toString(16);
			 while(hashtext.length()< 32)
			 {
			 hashtext = "0" + hashtext;
			 }
			 System.out.println("Enc is:"+hashtext);
			 usersign.setPassword(hashtext);
			User u= repo.findByMob(usersign.getUserMob(), usersign.getPassword());
			if(u!=null)
			{
				return new ResponseEntity<>("Login Successful",HttpStatus.OK);
				
			}else
			{
				return new ResponseEntity<>("Login Failed",HttpStatus.NOT_FOUND);
			}
		} catch (NoSuchAlgorithmException e) {
			return new ResponseEntity<>("Login Failed",HttpStatus.NOT_FOUND);
		}		 
	}
	
//**********************New Email Mapping for Forgot_pass***********************//
	@PostMapping("/forg_email")
	public String resendEmail(@RequestBody User user) {
		try
			{
				mailService.sendEmail(user);
				String s = String.valueOf(mailService.otp);
				user.setOTP(s);
				repo.updateOtp(s,user.getEmail());
				return "Otp Send on your email!!!";
				}
	catch(Exception ex)
				{
					return "failed:"+ex.getMessage();
				}
}
	
//*****************Otp validation for forget password***************//
	@PostMapping("/forg_otpauth")
	public ResponseEntity <String> resetPassOtpValidate(@RequestBody OtpAuth otpauth){
	User f= repo.findByOtp(otpauth.getuOtp());
	if(f!=null)
	{
	return new ResponseEntity<>("OTP Verified, Please Change your Password",HttpStatus.OK);
	}else
	{
	return new ResponseEntity<>("OTP Verification Failed,Please Put Correct OTP or Press on Resend OTP",HttpStatus.NOT_FOUND);
	}
	}	
	
//****************************************POST******************************//

	public class Post_Controller {
		@Autowired
		private PostRepository postd;
		@GetMapping("/viewpost")
		public List<Postdata> GetPost(){
			return postd.findAll();
		}

		@DeleteMapping("/deletepost/{postId}")
		public String deletePost(@PathVariable("postId") Long postId)
		{
			postd.deleteById(postId);
			return "Post deleted";
		}
		@PostMapping("/upload")
		public String Uploadpost(@RequestBody Postdata postdata)
		{
			postd.save(postdata);
			return("Post uploaded");
		}
	}
//************************************Advertise******************************//

		 class Advertise_Controller {
			@Autowired
			private AdvertiseRepository ad;
			
			@GetMapping("/advertise")
			public List<Advertise> GetAdvertise() {
			return ad.findAll();
			}
			@DeleteMapping("advertise/{id}")
			public String deleteCourse(@PathVariable("id") Long id)
			    {

				ad.deleteById(id);
				return "Successfully deleted!";
				}
			
			@PostMapping("/advertise")
			public String PostAdvertise(@RequestBody Advertise advertise)
			{
				ad.save(advertise);
				return "Advertise Uploaded";
				}
		}
//****************************************Advance Profile******************************//

		 @RestController
		 public class AdvanceProf_Controller {
		 	
		 	@Autowired
		 	private AdvanceProf_Repository advrepo;
		 	
		 	@PostMapping("/advanceprofile")
		 	public String Uploadprofile(@RequestBody Adv_prof adv)
		 	{
		 	advrepo.save(adv);
		 	return("Profile uploaded");
		 	}

		 	@GetMapping("/viewprofiles")
		 	public List<Adv_prof> GetProfile() {
		 	return advrepo.findAll();
		 	}

		 	@DeleteMapping("/deleteprofile/{user_id}")
		 	public String deleteprofile(@PathVariable("user_id") Long user_id)
		 	{
		 	advrepo.deleteById(user_id);
		 	return "Profile deleted";
		 	}
		 	@PutMapping("/advanceprofile")
			public String UpdateProfile(@RequestBody Adv_prof adv)
			{
				advrepo.save(adv);
				return("Profile updated");
			}
		 }

//--------------------------------------------------------------------------------//
//	search function

		 @GetMapping("/search")
		 public ResponseEntity <List<User>> searchname(@RequestParam String name){
			 return new ResponseEntity<List<User>>(repo.findbyName(name), HttpStatus.OK);	
		 }
}