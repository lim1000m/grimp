package com.ese.config.web;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.ese.config.spring.DataSourceConfig;
import com.ese.config.spring.DispatcherConfig;
import com.ese.config.spring.WebappConfig;
 
public class ServletConfig implements WebApplicationInitializer {
 
    public void onStartup(ServletContext servletContext)
            throws ServletException {
 
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebappConfig.class);
        context.register(DataSourceConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));

        AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
        dispatcherServletContext.register(DispatcherConfig.class);
        
        FilterRegistration.Dynamic fr = servletContext.addFilter("CHARACTER_ENCODING_FILTER", CharacterEncodingFilter.class);
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForServletNames(null, false, "/*");
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("AppServlet", new DispatcherServlet(dispatcherServletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.do");
        dispatcher.setInitParameter("dispatchOptionsRequest", "true"); 
    }
}
