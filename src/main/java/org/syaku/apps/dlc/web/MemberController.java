package org.syaku.apps.dlc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		List<SessionInformation> activeSessions = new ArrayList<>();
		for(Object principal : sessionRegistry.getAllPrincipals()) {
			activeSessions.addAll(sessionRegistry.getAllSessions(principal, false));
		}

		List<Map<String, Object>> visitors = new ArrayList<>();
		for(SessionInformation sessionInformation : activeSessions) {
			Map<String, Object> data = new HashMap<>();

			data.put("lastRequest", sessionInformation.getLastRequest());
			data.put("sessionId", sessionInformation.getSessionId());

			Object principalObj = sessionInformation.getPrincipal();
			if (principalObj instanceof User) {
				User user = (User) principalObj;
				data.put("username", user.getUsername());
			}

			visitors.add(data);
		}

		model.addAttribute("visitors", visitors);

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
}
