/**
 * 
 */
package kr.co.lge.goldstar.core.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author issuettl
 *
 */
public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		servletContext.setInitParameter("webAppRootKey", "lge-goldstar");
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"lge-goldstar", new DispatcherServlet(getContext()));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.toString());
        characterEncodingFilter.setForceEncoding(true);
        
        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter)
        .addMappingForUrlPatterns(null, false, "/*");
        
        servletContext.setSessionTimeout(60);
        //servletContext.getSessionCookieConfig().setPath("/");
	}    
	
	private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocations(
        		"kr.co.lge.goldstar.core.config");
        return context;
    }
}