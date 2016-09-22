package org.syaku.apps.hello.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 21.
 */
@Controller
public class HelloWorldController {

	@RequestMapping(value = "/hello",  method = RequestMethod.GET)
	public String dispHelloWorld(Model model) {
		model.addAttribute("title", "안녕하세요.");
		return "hello";
	}
}
