package com.mysite.bplus;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.bplus.writing.WritingService;
@SpringBootTest
class CommunityboardApplicationTests {

	 @Autowired
	    private WritingService writingService;

	    @Test
	    void testJpa() {        
	    	for(int i=1;i<=300;i++) {
	    		String subject= String.format("테스트 데이터:[%03d]", i);
	    		String content="내용없음";
	    		this.writingService.create(subject, content, null);
	    	}
	    
	    }

}
