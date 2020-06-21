package net.gentledot.keepsd.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi boardOpenApi() {
        String[] paths = {"/board/**"};

        return GroupedOpenApi.builder()
                .group("board")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI setCustomOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Keep SD (Social Distancing)")
                        .description("일상 속 사회적 거리두기를 보조할 수 있는 Application을 테마로 제작중.")
                        .version("v0.0.1"));
    }
}
