package com.fastcampus.projectboard.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;


@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties) {

        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        defaultTemplateResolver.setPrefix("classpath:/templates/");
        defaultTemplateResolver.setSuffix(".html");
        defaultTemplateResolver.setTemplateMode(TemplateMode.HTML);
        defaultTemplateResolver.setCacheable(false); // 개발 중 캐시 비활성화
        defaultTemplateResolver.setCharacterEncoding("UTF-8");

        return defaultTemplateResolver;
    }

    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private boolean decoupledLogic;

        public boolean isDecoupledLogic() {
            return decoupledLogic;
        }

        public void setDecoupledLogic(boolean decoupledLogic) {
            this.decoupledLogic = decoupledLogic;
        }
    }
}

