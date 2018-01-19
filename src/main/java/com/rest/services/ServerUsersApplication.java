package com.rest.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.context.support.ResourceBundleMessageSource;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.service.Contact;

@SpringBootApplication
@EnableSwagger2
public class ServerUsersApplication {

	private static final Contact DEFAULT_CONTACT = new Contact("Users API", "", "users@gmail.com");
	private static final ApiInfo DEFAULT_INFO_API = new ApiInfo("Great API",
			"It's a user great API",
			"1.0",
			"fdsa",
			 DEFAULT_CONTACT,
			"Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0",
			Arrays.asList(new StringVendorExtension("UsersAttributes", "more description can go here")));

	private static final Set<String> API_CONSUMES_PRODUCES = new HashSet(Arrays.asList("application/json", "application/xml"));

	public static void main(String[] args) {
		SpringApplication.run(ServerUsersApplication.class, args);
	}




	@Bean
	public LocaleResolver getLocaleResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}

	@Bean
	public Docket getDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_INFO_API)
				.consumes(API_CONSUMES_PRODUCES)
				.produces(API_CONSUMES_PRODUCES);
	}

}
