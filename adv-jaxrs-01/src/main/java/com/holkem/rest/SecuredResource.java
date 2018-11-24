package com.holkem.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("secured")
public class SecuredResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String secureMethod() {
		return "secure!";
	}
}
