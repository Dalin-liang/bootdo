package org.jasig.cas.client.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class ShiroSessionListener extends SessionListenerAdapter {

	private SessionMappingStorage sessionMappingStorage;

	@Override
	public void onExpiration(Session session) {

		super.onExpiration(session);
		/*if (sessionMappingStorage == null) {
			sessionMappingStorage = getSessionMappingStorage();
		}
		sessionMappingStorage.removeBySessionById(String.valueOf(session.getId()));*/
	}

	@Override
	public void onStop(Session session) {
		super.onStop(session);
		/*if (sessionMappingStorage == null) {
			sessionMappingStorage = getSessionMappingStorage();
		}
		sessionMappingStorage.removeBySessionById(String.valueOf(session.getId()));*/

	}

	protected static SessionMappingStorage getSessionMappingStorage() {
		return SingleSignOutFilter.getSingleSignOutHandler().getSessionMappingStorage();
	}
}
