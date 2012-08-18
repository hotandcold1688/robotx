package com.macyou.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/knowledgeAdmin.htm")
public class KnowledgeAdmin {
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		System.out.println("aaa");
		return "admin/knowledgeAdmin";
	}
}
