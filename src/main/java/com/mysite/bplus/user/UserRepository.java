package com.mysite.bplus.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByloginId(String loginId);
	Optional<SiteUser> findByusername(String username);
}
