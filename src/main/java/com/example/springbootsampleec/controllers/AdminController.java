package com.example.springbootsampleec.controllers;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;

import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.forms.ItemCreateForm;
import com.example.springbootsampleec.forms.ItemEditForm;
import com.example.springbootsampleec.services.ItemService;
 
@Controller
public class AdminController { 
    private final ItemService itemService;
    
    public AdminController(
        ItemService itemService
    ) {
        this.itemService = itemService;
    }
    
    @GetMapping("/admin")    
    public String admin(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
        List<Item> items = itemService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("title", "管理ページ");
        model.addAttribute("main", "admins/admin::main");
        return "layout/logged_in";    
    }
}