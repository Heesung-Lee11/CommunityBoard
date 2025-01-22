package com.mysite.bplus.writing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WritingRepository extends JpaRepository<Writing, Integer> {

	Writing findBySubject(String subject);
	Writing findBySubjectAndContent(String subject,String content);
	List<Writing> findBySubjectLike(String subject);
	Page<Writing> findAll(Pageable pageable);	
}
