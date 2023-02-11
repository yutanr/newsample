package com.example.springbootsampleec.controllers;
 
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.forms.SignUpForm;
import com.example.springbootsampleec.services.UserService;
 
@RequestMapping("/users")
@Controller
public class UserController { 
    // UserController 内で UserService を利用できるように
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/sign_up")    
    public String signUp(
        @ModelAttribute("signUpForm") SignUpForm signUpForm,
        Model model
        ) {
        model.addAttribute("title", "サインアップ");
        model.addAttribute("main", "users/sign_up::main");
        return "layout/not_logged_in";    
    }
    
    // サインアップフォーム投稿時の処理を追記
    @PostMapping("/sign_up")
    public String signUpProcess(
        @ModelAttribute("sign_up") SignUpForm signUpForm,
        RedirectAttributes redirectAttributes,
        Model model){
        String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
        userService.register(
            signUpForm.getName(),
            signUpForm.getEmail(),
            signUpForm.getPassword(),
            roles);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "アカウントの登録が完了しました");
        return "redirect:/users/login";
    }
 
    @GetMapping("/login")    
    public String login(Model model) {
        model.addAttribute("title", "ログイン");
        model.addAttribute("main", "users/login::main");
        return "layout/not_logged_in";    
    }
 
    @GetMapping("/detail/{id}")    
    public String detail(
        @AuthenticationPrincipal(expression = "user") User loginUser,
        @PathVariable("id")  Integer id,
        Model model
    ) {
        User user = userService.findById(id).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("title", "ブックマーク");
        model.addAttribute("main", "users/detail::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/edit/{id}")    
    public String edit(Model model) {
        model.addAttribute("title", "ユーザー情報を編集");
        model.addAttribute("main", "users/edit::main");
        return "layout/logged_in";    
    }
    
    @GetMapping("/cart/{id}")
    public String cart(
            @AuthenticationPrincipal(expression = "user") User loginUser,
            @PathVariable("id")  Integer id,
            Model model) {
            User user = userService.findById(id).orElseThrow();
            model.addAttribute("user", user);
            model.addAttribute("title", "カート");
            model.addAttribute("main", "users/cart::main");
            return "layout/logged_in";    
        }

}