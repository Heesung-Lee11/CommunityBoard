package com.mysite.bplus.writing;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import com.mysite.bplus.comment.CommentForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.security.Principal;
import com.mysite.bplus.user.SiteUser;
import com.mysite.bplus.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@RequiredArgsConstructor
@Controller
public class WritingController {

	private final WritingService writingService;
	private final UserService userService;
   
	@GetMapping("/news/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Writing> paging = this.writingService.getList(page);
        model.addAttribute("paging", paging);
        return "news_list";
    }
	
	@GetMapping(value = "/news/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
		Writing writing = this.writingService.getWriting(id);
        model.addAttribute("writing", writing);
		return "news_detail";
    }
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/news/create")
    public String writingCreate(WritingForm writingForm) {
        return "news_form";
    }
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/news/create")
    public String writingCreate(@Valid WritingForm writingForm, 
    		BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
            return "news_form";
        }
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.writingService.create(writingForm.getSubject(),writingForm.getContent(), siteUser);
        return "redirect:/news/list"; // 질문 저장후 질문목록으로 이동
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/news/modify/{id}")
    public String writingModify(WritingForm writingForm, @PathVariable("id") Integer id, Principal principal) {
        Writing writing = this.writingService.getWriting(id);
        if(!writing.getAuthor().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        writingForm.setSubject(writing.getSubject());
        writingForm.setContent(writing.getContent());
        return "news_form";
    }
	
	@PreAuthorize("isAuthenticated()")
    @PostMapping("/news/modify/{id}")
    public String writingModify(@Valid WritingForm writingForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "news_form";
        }
        Writing writing = this.writingService.getWriting(id);
        if (!writing.getAuthor().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.writingService.modify(writing, writingForm.getSubject(), writingForm.getContent());
        return String.format("redirect:/news/detail/%s", id);
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/news/delete/{id}")
    public String writingDelete(Principal principal, @PathVariable("id") Integer id) {
        Writing writing = this.writingService.getWriting(id);
        if (!writing.getAuthor().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.writingService.delete(writing);
        return "redirect:/";
    }
}
