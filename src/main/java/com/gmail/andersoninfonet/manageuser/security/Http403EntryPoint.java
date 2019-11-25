package com.gmail.andersoninfonet.manageuser.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class Http403EntryPoint extends Http403ForbiddenEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Permissão necessaria. Você não esta autorizado a acessar a URL solicitada." );
	}
}
