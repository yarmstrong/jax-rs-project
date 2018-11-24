package com.holkem.util;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_BASIC = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		/* holkem: specific url only will be processed for security checking */
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {

			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if (authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);

				if (authToken.contains(AUTHORIZATION_HEADER_BASIC)) {
					authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_BASIC, "");
					String decodedAuth = Base64.decodeAsString(authToken);

					String[] split = decodedAuth.split(":");
					String username = split[0];
					String password = split[1];

					/* do authentication against database or a business service 
					 * (here manual checking only to validate security checking
					 * filter works) */
					if ("user".equals(username) && "password".equals(password)) {
						return; // exiting means Jersey is free to proceed on its process
					}	
				} else {
					// do non-basic authorization logic here
				}
			}
			
			// create Response with appropriate error code
			Response unauthorizedResponse = Response
					.status(Status.UNAUTHORIZED)
					.entity("User cannot access the resource.")
					.build();
			requestContext.abortWith(unauthorizedResponse);	
		}
	}
}
