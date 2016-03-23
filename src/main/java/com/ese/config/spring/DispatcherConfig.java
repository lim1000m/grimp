package com.ese.config.spring;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.ese.config.resolverHandler.RequestArgumentResolver;

import c.e.g.grimp.Grimp;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.ese", useDefaultFilters=false, includeFilters={@Filter(Controller.class)})
public class DispatcherConfig  extends WebMvcConfigurerAdapter{
	
	@Autowired
	private WebApplicationContext was;
	
	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
	
	/**
	 * ViewResolver
	 * @Method Name : viewResolver
	 * @create Date : 2015. 7. 23.
	 * @made by : GOEDOKID
	 * @explain : 
	 * @param : 
	 * @return : ViewResolver
	 */
	@Bean
	public ViewResolver viewResolver()  {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	/**
	 * forward by beanNameViwResolver. using the beanName create in spring configuration.
	 * @Method Name : beanNameViewResolver
	 * @create Date : 2015. 7. 23.
	 * @made by : GOEDOKID
	 * @explain : 
	 * @param : 
	 * @return : BeanNameViewResolver
	 */
	@Bean
	public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(0);
		return beanNameViewResolver;
	}
	
	/**
	 * Configuration mapping using by ParameterizableViewController
	 * no need any special business logic.
	 * @author ESE-MILLER
	 * @see NOW TESTING
	 * @category configMethod
	 * @since 2014. 04. 25
	 * @return void
	 */
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("main/main");
	}
	
	/**
	 * Configuration the LocaleInterceptor
	 * This configuration is very powerful and flexible then setting interceptor into  RequestMappingHandlerMapping configuration
	 * This Interceptor is called InterceptorRegistory
	 * powerful, flexible and possible mapping interceptor each URL
	 * If you don't setting the URI Pattern, mapping all URI to Interceptor  
	 * @author ESE-MILLER
	 * @category configMethod
	 * @since 2014. 04. 25
	 * @return void
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		InterceptorRegistration registration = registry.addInterceptor(localeChangeInterceptor());
		registration.addPathPatterns("/grimp/**");
	}
	
	/**
	 * Three type of resolver first one is SessionLocaleResolver and another one is CookieResolver etc.
	 * Setting "kr" default locale 
	 * @Method Name : localeResolver
	 * @create Date : 2015. 7. 23.
	 * @made by : GOEDOKID
	 * @explain : 
	 * @param : 
	 * @return : SessionLocaleResolver
	 */
	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(new Locale("kr"));
		return sessionLocaleResolver;
	}
	
	/**
	 * Configuration HandlerInterceptor
	 * Before return the locale 
	 * catch the request and setting the locale came form user side
	 * and No need to carry the locale receive from user side
	 * Cause, LocaleChangeInterceptor catch the locale at first then setting in request.
	 * No need to set the parameter like "&lang=kr"
	 * @author ESE-MILLER
	 * @category configMethod
	 * @since 2014. 04. 25.
	 * @return HandlerInterceptor 
	 */
	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor;
		localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	/**
	 * MessageSource
	 * @Method Name : messageSource
	 * @create Date : 2015. 7. 23.
	 * @made by : GOEDOKID
	 * @explain : 
	 * @param : 
	 * @return : ResourceBundleMessageSource
	 */
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource;
		messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("grimp/messages/message");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
	}
	
	
	/**
	 * 파일 업로더
	 * @Method Name : fileUpload
	 * @create Date : 2015. 8. 21.
	 * @made by : GOEDOKID
	 * @explain : 
	 * @param : 
	 * @return : CommonsMultipartResolver
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(11000000);
		return multipartResolver;
	}
	
	/**
	 * @Method Name : grimp
	 * @create Date : 2016. 3. 15.
	 * @made by : "GOEDOKID"
	 * @explain : Grimp Bean 생성 
	 * @param : 
	 * @return : Grimp
	 */
	@Bean
	public Grimp grimp() {
		return new Grimp(messageSource());
	}
	
	/**
	 * @Method Name : fileUpload
	 * @create Date : 2015. 8. 21.
	 * @made by : GOEDOKID
	 * @explain : Argument Resolver 설정 
	 * @param : List<HandlerMethodArgumentResolver> argumentResolvers
	 * @return : CommonsMultipartResolver
	 */
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(requestArgumentResolver());
    }
	
	/**
	 * @Method Name : commomArgumentResolver
	 * @create Date : 2016. 3. 18.
	 * @made by : "GOEDOKID"
	 * @explain : 공통 파라메타 Argument Resolver
	 * @param : 
	 * @return : HandlerMethodArgumentResolver
	 */
	@Bean
	public HandlerMethodArgumentResolver requestArgumentResolver() {
		return new RequestArgumentResolver();
	}
}