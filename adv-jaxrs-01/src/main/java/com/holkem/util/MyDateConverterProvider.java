package com.holkem.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.holkem.model.MyDate;

@Provider
public class MyDateConverterProvider implements ParamConverterProvider {

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (rawType.getName().equals(MyDate.class.getName())) {
			// must return a ParamConverter<T> : do anonymous class instead of creating a separate class
			return new ParamConverter<T>() {

				@Override
				public T fromString(String value) {
					LocalDate requestedDate = LocalDate.now(ZoneId.systemDefault());
					if ("tomorrow".equalsIgnoreCase(value)) {
						requestedDate = requestedDate.plusDays(1);
					} else if ("yesterday".equalsIgnoreCase(value)) {
						requestedDate = requestedDate.minusDays(1);
					}
					
					MyDate myDate = new MyDate();
					myDate.setDate(requestedDate.getDayOfMonth());
					myDate.setMonth(requestedDate.getMonthValue());
					myDate.setYear(requestedDate.getYear());
					
					return rawType.cast(myDate);
				}

				@Override
				public String toString(T value) {
					if (value == null) return null;
					return value.toString();
				}
				
			};
		}
		return null;
	}

}
