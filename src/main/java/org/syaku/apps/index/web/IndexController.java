package org.syaku.apps.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
@Controller
public class IndexController {

	@RequestMapping(value = "/404",  method = RequestMethod.GET)
	public String disp404() {
		return "404";
	}
}
