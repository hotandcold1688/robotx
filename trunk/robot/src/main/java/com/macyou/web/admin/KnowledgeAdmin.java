package com.macyou.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/knowledge")
public class KnowledgeAdmin {
	@RequestMapping(value = "/query.htm", method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		model.put("name", "dzl");
		return "admin/knowledge/query";
	}

	@RequestMapping(value = "/test2.htm", method = RequestMethod.GET)
	public String initForm2(ModelMap model) {
		System.out.println("2");
		return "admin/knowledgeAdmin";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleForm(ModelMap model) {
		return "admin/knowledgeAdmin";
	}
}
