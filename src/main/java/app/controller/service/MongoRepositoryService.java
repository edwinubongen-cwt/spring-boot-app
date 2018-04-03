package app.controller.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Service
public class MongoRepositoryService {

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> testCollection;
	
	public MongoRepositoryService() {
		System.out.println("Setting the testCollection...");
		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase("mydb");
		testCollection = database.getCollection("test");
		System.out.println("Setting the testCollection. - COMPLETED");
	}

	public MongoCollection<Document> getCollection() {
		return testCollection;
	}

	public void insertDocument() {
		System.out.println("Inserting one document...");
		Document doc = new Document("name", "document0").append("type", "database").append("count", 1)
				.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
				.append("info", new Document("x", 203).append("y", 102));
		testCollection.insertOne(doc);
		System.out.println("Insert successful.");
	}

	public void insertManyDocuments() {
		System.out.println("Inserting two documents...");
		Document doc1 = new Document("name", "document2").append("type", "database").append("count", 1)
				.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
				.append("info", new Document("x", 203).append("y", 102));
		Document doc2 = new Document("name", "document3").append("type", "database").append("count", 1)
				.append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
				.append("info", new Document("x", 203).append("y", 102));
		List<Document> documents = new ArrayList<Document>();
		documents.add(doc1);
		documents.add(doc2);
		testCollection.insertMany(documents);
		System.out.println("Insert successful.");
	}
	
	public Document getFirstDocument() {
		System.out.println("Retrieving first document...");
		return testCollection.find().first();
	}
	
	public String getDocuments() {
		System.out.println("Retrieving documents...");
		StringBuffer documents = new StringBuffer();
		MongoCursor<Document> cursor = testCollection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		    	documents.append(cursor.next().toJson());
		    	documents.append(System.lineSeparator());
		    }
		} finally {
		    cursor.close();
		}
		return documents.toString();
	}
	
	public UpdateResult updateDocument() {
		System.out.println("Updating document...");
		return testCollection.updateOne(eq("name", "document0"), new Document("$set", new Document("name", "document1")));
	}
	
	public long updateManyDocuments() {
		System.out.println("Updating documents...");
		return testCollection.updateMany(eq("type", "database"), inc("count", 1)).getModifiedCount();
	}
	
	public DeleteResult deleteDocument() {
		System.out.println("Deleting document...");
		return testCollection.deleteOne(eq("name", "document1"));
	}
	
	public DeleteResult deleteDocuments() {
		System.out.println("Deleting documents...");
		return testCollection.deleteMany(eq("type", "database"));
	}
	
	// TEST
}
