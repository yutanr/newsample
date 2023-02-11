package com.example.sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@ResponseBody
	@GetMapping("/")
	public String index() {
	return "初めてのSpringBoot";
}
}
