package com.macyou.web.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/knowledge")
public class KnowledgeAdmin {
	@RequestMapping(value = "/query.htm", method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		model.put("name", "dzl");
		return "admin/knowledge/query";
	}

	@RequestMapping(value = "/insert.htm", method = RequestMethod.GET)
	public String initForm2(ModelMap model) {
		return "admin/knowledge/insert";
	}

	@RequestMapping(value = "/insert.htm", method = RequestMethod.POST)
	public String handleForm(@RequestParam("question") String question, @RequestParam("answer") String answer,
			ModelMap model) {
		System.out.println(question + ":" + answer);
		return "admin/knowledge/insert";
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(URLDecoder.decode("%26%2320154%3B%26%2327665%3B","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
