package com.mysite.bplus.comment;

import com.mysite.bplus.writing.Writing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import com.mysite.bplus.user.SiteUser;
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public void create(Writing writing, String content, SiteUser author) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setWriting(writing);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
    }
}