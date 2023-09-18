/**
 * 
 */
package kr.co.lge.goldstar.core.config.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import kr.co.rebel9.web.bind.support.WebBindingInitializer;

/**
 * @author issuettl
 *
 */
@Configuration
@EnableWebMvc
public class WebBindConfiguration extends WebMvcConfigurationSupport {

	/**
	 * @return 바인딩 초기화
	 */
	@Bean
	public WebBindingInitializer webBindingInitializer(){
		return new WebBindingInitializer();
	}
	
	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter(
			@Qualifier("mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager,
			@Qualifier("mvcConversionService") FormattingConversionService conversionService,
			@Qualifier("mvcValidator") Validator validator) {
		RequestMappingHandlerAdapter handlerAdapter = super.requestMappingHandlerAdapter(contentNegotiationManager, conversionService, validator);
		handlerAdapter.setWebBindingInitializer(webBindingInitializer());
		
		return handlerAdapter;
	}
}
