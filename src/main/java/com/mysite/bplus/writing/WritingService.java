package com.mysite.bplus.writing;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import com.mysite.bplus.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import com.mysite.bplus.user.SiteUser;

@RequiredArgsConstructor
@Service
public class WritingService {

    private final WritingRepository writingRepository;

    public Page<Writing> getList(int page) {
    	List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
    	Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return this.writingRepository.findAll(pageable);
    }
    
    public Writing getWriting(Integer id) {  
        Optional<Writing> writing = this.writingRepository.findById(id);
        if (writing.isPresent()) {
            return writing.get();
        } else {
            throw new DataNotFoundException("writing not found");
        }
    }
    
    public void create(String subject, String content, SiteUser user) {
        Writing w = new Writing();
        w.setSubject(subject);
        w.setContent(content);
        w.setCreateDate(LocalDateTime.now());
        w.setAuthor(user);
        this.writingRepository.save(w);
    }
    
    public void modify(Writing writing, String subject, String content) {
        writing.setSubject(subject);
        writing.setContent(content);
        writing.setModifyDate(LocalDateTime.now());
        this.writingRepository.save(writing);
    }
    
    public void delete(Writing writing) {
        this.writingRepository.delete(writing);
    }
}
