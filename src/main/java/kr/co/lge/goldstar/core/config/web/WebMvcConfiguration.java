/**
 * 
 */
package kr.co.lge.goldstar.core.config.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import kr.co.lge.goldstar.core.interceptor.ManagerInHandlerInterceptor;
import kr.co.lge.goldstar.core.interceptor.ManagerPageHandlerInterceptor;
import kr.co.lge.goldstar.core.interceptor.SignInHandlerInterceptor;
import kr.co.lge.goldstar.core.interceptor.UserPageHandlerInterceptor;
import kr.co.rebel9.web.method.support.MultipartArgumentResolver;
import kr.co.rebel9.web.method.support.MultipartsArgumentResolver;
import kr.co.rebel9.web.servlet.WebHandlerInterceptor;

/**
 * @author issuettl
 *
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	private static final int MB = 1024*1024;

	@Value("${multipart.size.max.files}")
	private long maxUploadFilesSize;
	
	@Value("${multipart.size.max.file}")
	private long maxUploadFileSize;
	
	@Value("${system.domain}")
	private String systemDomain;
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", systemDomain + "/u/page/index.do");
        registry.addRedirectViewController("/dash", systemDomain + "/m/dash.do");
        registry.addRedirectViewController("/dash/", systemDomain + "/m/dash.do");
    }
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		//argumentResolvers.add(new DataMapArgumentResolver());
		argumentResolvers.add(new MultipartArgumentResolver());
		argumentResolvers.add(new MultipartsArgumentResolver());
	}
	
	@Bean
	WebHandlerInterceptor webHandlerInterceptor() {
		return new WebHandlerInterceptor();
	}
	
	@Bean
	UserPageHandlerInterceptor userPageHandlerInterceptor() {
		return new UserPageHandlerInterceptor();
	}
	
	@Bean
	ManagerPageHandlerInterceptor managerPageHandlerInterceptor() {
		return new ManagerPageHandlerInterceptor();
	}
	
	@Bean
	SignInHandlerInterceptor signInHandlerInterceptor() {
		return new SignInHandlerInterceptor();
	}
	
	@Bean
	ManagerInHandlerInterceptor managerInHandlerInterceptor() {
		return new ManagerInHandlerInterceptor();
	}
	/*
	@Bean
	AdminWebHandlerInterceptor adminWebHandlerInterceptor() {
		return new AdminWebHandlerInterceptor();
	}*/
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(webHandlerInterceptor()).addPathPatterns("/**");
		//registry.addInterceptor(elespaceWebHandlerInterceptor()).addPathPatterns("/page/**");
		//registry.addInterceptor(adminWebHandlerInterceptor()).addPathPatterns("/page/**", "/rest/**");
		registry.addInterceptor(userPageHandlerInterceptor()).addPathPatterns("/u/**");
		registry.addInterceptor(signInHandlerInterceptor()).addPathPatterns("/u/**");
		registry.addInterceptor(managerInHandlerInterceptor()).addPathPatterns("/m/**");
		registry.addInterceptor(managerPageHandlerInterceptor()).addPathPatterns("/m/**");
	}
	
	/**
	 * @return JSTL 뷰
	 */
	@Bean
    public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver viewResolver = new BeanNameViewResolver();
		viewResolver.setOrder(1);
        return viewResolver;
    }
	
	/**
	 * @return JSTL 뷰
	 */
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/pages/");
        viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
        return viewResolver;
    }
	
	/**
	 * @return 첨부파일
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(this.maxUploadFilesSize * MB);
		multipartResolver.setMaxUploadSizePerFile(this.maxUploadFileSize * MB);
		
	    return multipartResolver;
	}
	
	@Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
