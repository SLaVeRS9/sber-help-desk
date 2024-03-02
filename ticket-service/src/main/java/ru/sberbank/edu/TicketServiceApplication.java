package ru.sberbank.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
/*import springfox.documentation.swagger2.annotations.EnableSwagger2;*/

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
/*@EnableSwagger2*/
public class TicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}

}
