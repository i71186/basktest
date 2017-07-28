package demoboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("demoboot, com.iso.claimsearch")
public class DemoBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoBootApplication.class, args);
  }
}
