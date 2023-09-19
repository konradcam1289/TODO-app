package github.konradcam1289.todoapp.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Component
public class MvcConfiguration implements WebMvcConfigurer {
    private Set<HandlerInterceptor> interceptors;

    public MvcConfiguration(final Set<HandlerInterceptor>
                                    interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        interceptors.stream().forEach(registry::addInterceptor);
    }
}
