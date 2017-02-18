package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if( !HttpSessionUtils.isLoginUser(session) ) {
			return "/user/login";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if( !HttpSessionUtils.isLoginUser(session) ) {
			return "/user/login";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		try {
			hasPermission(session, question);
		}
		catch( IllegalStateException e ) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		try {
			hasPermission(session, question);
		}
		catch( IllegalStateException e ) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		try {
			hasPermission(session, question);
		}
		catch( IllegalStateException e ) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		questionRepository.delete(id);
		return "redirect:/";
	}

	private void hasPermission(HttpSession session, Question question) {
		if( !HttpSessionUtils.isLoginUser(session) ) {
			throw new IllegalStateException("로그인이 필요 합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if( !question.isSameWriter(loginUser) ) {
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능 합니다.");
		}
	}
}
