package app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@RequestMapping("/app-info")
	public String index() {
		System.out.println("spring-boot-app-1.0.1-SNAPSHOT");
		return "spring-boot-app-1.0.1-SNAPSHOT";
	}

}