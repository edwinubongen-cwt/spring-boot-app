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
    public String testConnection() {
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
	
	@RequestMapping("/create-document")
    public void createRecord() {
		mongoRepositoryService.insertDocument();
    }

	@RequestMapping("/create-many-documents")
    public void createManyRecords() {
		mongoRepositoryService.insertManyDocuments();
    }
	
	@RequestMapping("/get-first-document")
    public String getFirstDocument() {
		return mongoRepositoryService.getFirstDocument().toJson();
    }
	
	@RequestMapping("/get-documents")
    public String getDocuments() {
		return mongoRepositoryService.getDocuments();
    }
	
	@RequestMapping("/update-document")
    public boolean updateDocument() {
		return mongoRepositoryService.updateDocument().wasAcknowledged();
    }

	@RequestMapping("/update-documents")
    public long updateManyDocuments() {
		return mongoRepositoryService.updateManyDocuments();
    }
	
	@RequestMapping("/delete-document")
    public boolean deleteDocument() {
		return mongoRepositoryService.deleteDocument().wasAcknowledged();
    }
	
	@RequestMapping("/delete-documents")
    public long deleteDocuments() {
		return mongoRepositoryService.deleteDocuments().getDeletedCount();
    }
}
