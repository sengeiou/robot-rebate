package org.jeecg.modules.robot.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.jeecg.modules.robot.annotation.DefaultSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@Slf4j
@Configuration
public class ServletConfiguration implements WebMvcConfigurer {

    @Autowired
    private ThreadPoolTaskExecutor mvcThreadPoolTaskExecutor;

    /*
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;*/

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        if (mvcThreadPoolTaskExecutor != null) {
            configurer.setTaskExecutor(mvcThreadPoolTaskExecutor);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultSessionInterceptor());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(
            new EmojiErasureObjectMapper().enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)));
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .addProperty("hibernate.validator.fail_fast", "true")
            .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.serializers(new EmojiErasureObjectMapper.StringSerializer());
        builder.deserializers(new EmojiErasureObjectMapper.StringDeserializer());
        return builder.createXmlMapper(false).build();
    }
}
