package com.franzoo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("Select u from User u WHERE u.mob=?1 and u.password=?2")
	User findByMob(String mob,String password);
	
	@Query("Select u from User u WHERE u.OTP=?1")
	User findByOtp(String OTP);
	
	@Transactional
	@Modifying
	@Query("Update User u set u.OTP=?1 WHERE u.email=?2")
	void updateOtp(String OTP,String email);
	
//	@Query(value="SELECT * FROM User WHERE"+"MATCH(name"+"AGANIST(?1)",nativeQuery=true)
//	public List<User> findByKeyword(String keyword);
	@Query("SELECT u FROM User u WHERE u.name LIKE %?1%"
            + " OR u.email LIKE %?1%"
            + " OR CONCAT(u.mob, '') LIKE %?1%")
   List< User> findbyName(String name);
}
