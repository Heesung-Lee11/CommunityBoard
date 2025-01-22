package com.mysite.bplus.comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.validation.BindingResult;
import com.mysite.bplus.writing.Writing;
import com.mysite.bplus.writing.WritingService;
import com.mysite.bplus.user.SiteUser;
import com.mysite.bplus.user.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final WritingService writingService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, 
    		@Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
    	Writing writing = this.writingService.getWriting(id);
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	if (bindingResult.hasErrors()) {
            model.addAttribute("writing", writing);
            return "news_detail";
        }
        this.commentService.create(writing, commentForm.getContent(), siteUser);
        return String.format("redirect:/news/detail/%s", id);
    }
}
