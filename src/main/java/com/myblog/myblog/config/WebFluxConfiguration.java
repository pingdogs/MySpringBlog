package com.myblog.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.CodecConfigurer.CustomCodecs;
import org.springframework.http.codec.ServerCodecConfigurer.ServerDefaultCodecs;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.myblog.myblog.core.web.GlobalResponseBodyHandler;

import java.util.Collections;
import java.util.List;

@Configuration
public class WebFluxConfiguration implements WebFluxConfigurer {

	
	
	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
	   return ServerCodecConfigurer.create();
	}
	
	@Bean
	public RequestedContentTypeResolver webFluxContentTypeResolver() {
		RequestedContentTypeResolverBuilder builder = new RequestedContentTypeResolverBuilder();
		configureContentTypeResolver(builder);
		return builder.build();
	}
	
    @Bean
    public GlobalResponseBodyHandler responseWrapper(ServerCodecConfigurer serverCodecConfigurer,
                                                     RequestedContentTypeResolver requestedContentTypeResolver) {
        return new GlobalResponseBodyHandler(serverCodecConfigurer.getWriters(), requestedContentTypeResolver);
    }


    @Bean
    @Order(0) 
    public CorsWebFilter corsFilter() {
       
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*")); 
        config.setAllowCredentials(true); 
        config.addAllowedMethod("*"); 
        config.setAllowedHeaders(Collections.singletonList("*")); 
        config.setMaxAge(1800L); 
        source.registerCorsConfiguration("/**", config);
        
        return new CorsWebFilter(source); 
    }
}
