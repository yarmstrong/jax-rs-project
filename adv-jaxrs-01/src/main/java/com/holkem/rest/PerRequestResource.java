package com.holkem.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/* holkem: non-singleton resource is a per-request resource
 * new instance is created everytime an API request is made */
@Path("perrequest")
public class PerRequestResource {
	private int count;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testMethod() {
		return "Non-singleton method called " + ++count + " times.";
	}
}
