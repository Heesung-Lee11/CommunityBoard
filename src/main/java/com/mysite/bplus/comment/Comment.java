package com.mysite.bplus.comment;

import java.time.LocalDateTime;

import com.mysite.bplus.writing.Writing;
import com.mysite.bplus.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate; 
    
    private LocalDateTime modifyDate;

    @ManyToOne
    private Writing writing;  
    
    @ManyToOne
    private SiteUser author;
}