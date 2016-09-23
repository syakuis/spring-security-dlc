package org.syaku.apps.dlc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.syaku.spring.http.StatusCode;
import org.syaku.spring.http.SuccessBody;
import org.syaku.spring.security.session.SessionInformationSupport;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 21.
 */
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("#{config.usernameParameter}")
	String usernameParameter;
	@Value("#{config.passwordParameter}")
	String passwordParameter;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String dispMemberLogin() {
		return "member/login";
	}


	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String dispMemberMypage(Model model, HttpSession session) {
		model.addAttribute("sessionId", session.getId());
		return "member/mypage";
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.GET)
	public String dispMemberVisitor(Model model) {
		SessionInformationSupport sessionInformationSupport = new SessionInformationSupport(sessionRegistry);
		model.addAttribute("visitors", sessionInformationSupport.getSessionInformations());

		return "member/visitor";
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> procMemberVisitorDelete(@RequestBody Map< String, Object> data) {
		SessionInformation session = sessionRegistry.getSessionInformation((String) data.get("sessionId"));
		if (session != null) {
			session.expireNow();
		}

		return new HashMap<>();
	}

	@RequestMapping(value = "/login/duplication", method = RequestMethod.POST)
	public @ResponseBody SuccessBody procMemberLoginDuplication(@RequestBody MultiValueMap<String, String> params) {

		String username = params.getFirst(usernameParameter);
		String password = params.getFirst(passwordParameter);

		SuccessBody body = new SuccessBody();
		body.setStatusCode(StatusCode.DuplicationLogin);

		try {
			UserDetails details = userDetailsService.loadUserByUsername(username);

			if (details != null) {
				// 사용자 정보 일치여부
				if (details.getPassword().equals(password)) {

					// 이미 로그인 사용자 여부
					SessionInformationSupport sessionInformationSupport = new SessionInformationSupport(sessionRegistry);
					if (sessionInformationSupport.userExists(username)) {
						body.setError(true);
					}
				}
			}
		} catch (UsernameNotFoundException e) {
			// 없는 경우 로그인 처리.
		}

		return body;
	}

	@RequestMapping(value = "/error/{id}", method = RequestMethod.GET)
	public String dispMemberMypage(@PathVariable("id") String error) {
		return "error/" + error;
	}
}
