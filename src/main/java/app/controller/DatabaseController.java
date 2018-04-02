package app.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.controller.service.MongoRepositoryService;

@RestController
public class DatabaseController {
	
	@Autowired
	MongoRepositoryService mongoRepositoryService;
	
	@RequestMapping("/test-connection")
    public String index() {
		String output = "";
    	System.out.println("Testing connection...");
    	if (!Objects.isNull(mongoRepositoryService.getCollection())) {
    		output = "Connection Successful.";	
    	}
    	else {
    		output = "Connection failed.";
    	}
		System.out.println(output);
        return output;
    }
}
