package demoboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WelcomeWeb {
  @RequestMapping("/welcome")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  @RequestMapping("/fileupload")
  public ModelAndView demoPage() {

    ModelAndView viewClaimsearch = new ModelAndView("fileupload");

    return viewClaimsearch;
  }
}
