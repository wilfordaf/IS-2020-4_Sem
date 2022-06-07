package startapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("dao")
@ComponentScan({"controllers", "services", "dto", "startapp.cfg"})
@EntityScan("models")
public class CatsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatsSpringApplication.class, args);
    }
}

