package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// основной конфигурационный класс Spring-а, унаследован от WebMvcConfigurerAdapter, и наследует все его методы
@Configuration
public class RootConfig extends WebMvcConfigurerAdapter {

}