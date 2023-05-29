package com.mycgv_jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value="/index.do", method=RequestMethod.GET)
	public String index() {
		
		return "index";
	}
	
	@RequestMapping(value="/header.do", method=RequestMethod.GET)
	public String header() {
		return "header";
	}
	
	@RequestMapping(value="/footer.do", method=RequestMethod.GET)
	public String footer() {
		return "footer";
	}

}
