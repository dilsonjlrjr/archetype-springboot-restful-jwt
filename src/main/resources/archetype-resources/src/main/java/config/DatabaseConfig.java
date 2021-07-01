package ${package}.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("${package}.mapper")
public class DatabaseConfig {
}
