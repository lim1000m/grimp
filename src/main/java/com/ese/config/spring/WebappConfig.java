package com.ese.config.spring;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages="com.ese", excludeFilters={@Filter(Controller.class), @Filter(Configuration.class)} )
@EnableCaching
public class WebappConfig  {
	
	/**
	 * When the get the property message from the property file
	 * it's available to use the express such as ${...} @Value
	 * @author ESE-MILLER
	 * @category Method
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/**
	 * manage set of caches
	 * @author ESE-MILLER
	 * @category Method
	 * @return
	 */
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
}
