package com.cm4j.test.mvc.rest;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/class-mapping")
public class RestfulAction {

	/**
	 * http://localhost:8080/cm4j-test//class-mapping/t1/asd?v1=123
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 * @return
	 */
	@RequestMapping("/t1/{v2}")
	public ModelAndView t1(@RequestParam String v1, @PathVariable String v2, @RequestHeader("Host") String v3,
			@CookieValue("JSESSIONID") String v4) {
		ModelAndView modelAndView = new ModelAndView("showResult");
		modelAndView.addObject("v1", v1);
		modelAndView.addObject("v2", v2);
		modelAndView.addObject("v3", v3);
		modelAndView.addObject("v4", v4);
		return modelAndView;
	}

	/**
	 * 测试不成功
	 * http://localhost:8080/cm4j-test//class-mapping/t2/2011-5-7
	 */
	@RequestMapping("/t2/{date}")
	public @ResponseBody
	String date(@PathVariable @DateTimeFormat(iso = ISO.DATE) Date date) {
		return "converted date " + date;
	}
}
