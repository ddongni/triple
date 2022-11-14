package dongeun.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "여행지(도시) 관리 API 명세", version = "v1")
)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi openApi(){
        return GroupedOpenApi.builder()
                .group("dongeun")
                .pathsToMatch("/**")
                .packagesToScan("dongeun")
                .build();
    }
}