package com.holkem.rest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/* holkem: singleton resource
 * only instance is created to handle all API request */
@Path("singleton")
@Singleton
public class SingletonResource {
	private int count;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testMethod() {
		return "Singleton method called " + ++count + " times.";
	}
}
