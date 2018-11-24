package com.holkem.rest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.holkem.model.MyDate;

@Path("/date")
public class DateResource {
	
	/* holkem: Create MessageBodyWriter when such issue below is encountered:
	 * ie: returning Date to text/plain (intentionally did not use toString()
	 * method of Date to be forced to create custom MessageBodyWriter)
	 * MessageBodyWriter not found for media type=text/plain, 
	 * type=class java.util.Date, genericType=class java.util.Date. */
	/* holkem: Implementing a custom media type which would require creation
	 * of a MessageBodyWriter for converting return data type to the custom
	 * media type */
	@GET
	@Produces(value = { "text/shortdate", MediaType.TEXT_PLAIN })
	public Date getDateToday() {
		// Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/* holkem: Create ParamConveter when such issue below is encountered:
	 * ie: passing a path param (string) into a non-primitive data 
	 * WARNING: No injection source found for a parameter of type 
	 * public java.lang.String com.holkem.rest.DateResource
	 * .getRequestedDate(com.holkem.model.MyDate) */
	@GET
	@Path("/{dateString}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRequestedDate(@PathParam("dateString") MyDate myDate) {
		return "Got " + myDate; 
	}
	
}
