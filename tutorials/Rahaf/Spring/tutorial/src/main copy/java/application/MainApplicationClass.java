package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("javax.persistence.Entity")
//@EnableJpaRepositories("org.springframework.data.jpa.repository.JpaRepository")
@ComponentScan({"com.delivery.request"})
@EntityScan("com.delivery.domain")
@EnableJpaRepositories("com.delivery.repository")
public class MainApplicationClass {
    public static void main(String[] args)
    {
        SpringApplication.run(MainApplicationClass.class, args);
    }
}
