package fr.urssaf.image.commons.controller.spring3.exemple.resource;

import java.util.Date;

import org.springframework.format.support.FormattingConversionServiceFactoryBean;

public class MyFormattingConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		// conversion des dates en dd/MM/yyyy
		this.getObject().addFormatterForFieldType(Date.class,
				new DateFormatter());
		
	}
}
