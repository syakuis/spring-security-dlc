package org.syaku.spring.security.session;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class SessionInformationSupport {
	private final SessionRegistry sessionRegistry;

	public SessionInformationSupport(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public List<Information> getSessionInformations() {

		List<SessionInformation> sessionInformations = new ArrayList<>();
		for(Object principal : sessionRegistry.getAllPrincipals()) {
			sessionInformations.addAll(sessionRegistry.getAllSessions(principal, false));
		}

		List<Information> informations = new ArrayList<>();
		for(SessionInformation sessionInformation : sessionInformations) {


			Date lastRequest = sessionInformation.getLastRequest();
			String sessionId = sessionInformation.getSessionId();

			String username = null;

			Object principal = sessionInformation.getPrincipal();
			if (principal instanceof User) {
				User user = (User) principal;
				username = user.getUsername();
			}

			Information information = new Information(username, sessionId, lastRequest, principal);

			informations.add(information);
		}

		return informations;
	}
	public boolean userExists(String username) {
		for(Information information : getSessionInformations()) {
			String _username = information.getUsername();

			if (username.equals(_username)) {
				return true;
			}
		}

		return false;
	}
}
