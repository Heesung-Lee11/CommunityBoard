package com.mysite.bplus.writing;

import java.time.LocalDateTime;
import jakarta.persistence.ManyToOne;
import com.mysite.bplus.user.SiteUser;
import java.util.List;

import com.mysite.bplus.comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter; 

@Getter 
@Setter 
@Entity 
public class Writing { 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id; 

    @Column(length = 100) 
    private String subject; 

    @Column(columnDefinition = "TEXT") 
    private String content; 

    private LocalDateTime createDate; 
    
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "writing", cascade = CascadeType.REMOVE) 
    private List<Comment> commentList;
    
    @ManyToOne
    private SiteUser author;
    
    
}
