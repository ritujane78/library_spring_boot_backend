package com.jane.spring_boot_library.config;

import com.jane.spring_boot_library.entity.Book;
import com.jane.spring_boot_library.entity.Message;
import com.jane.spring_boot_library.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowedOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){

        HttpMethod[] theUnSupportedActions = {HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.POST,HttpMethod.PUT};

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Message.class);

        disableHttpMethods(Book.class, config, theUnSupportedActions);
        disableHttpMethods(Review.class, config, theUnSupportedActions);

        cors.addMapping(config.getBasePath()+ "/**")
                .allowedOrigins(theAllowedOrigins)
                .allowedMethods("GET");
    }

    private void disableHttpMethods(Class libClass, RepositoryRestConfiguration config, HttpMethod[] theUnSupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(libClass)
                .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(theUnSupportedActions)))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnSupportedActions)));
    }
}
