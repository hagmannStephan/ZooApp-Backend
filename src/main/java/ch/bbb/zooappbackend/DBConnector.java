package ch.bbb.zooappbackend;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

public class DBConnector {
    private static final String CONNECTION_STRING = "mongodb://root:root@localhost/";
    private static final String DATABASE_NAME = "zoo_RestAPI";
    private static final String COLLECTION_NAME = "tickets";

    public static ArrayList<Ticket> getTickets() {

        ArrayList<Ticket> results = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                MongoCollection<Document> carDocs = database.getCollection(COLLECTION_NAME);
                FindIterable<Document> iterDoc = carDocs.find();
                Iterator<Document> it = iterDoc.iterator();
                while (it.hasNext()) {
                    Document doc = it.next();
                    Ticket t = new Ticket();
                    t.setId(doc.getInteger("id"));
                    t.setDate(doc.getString("date"));
                    t.setNumAdults(doc.getInteger("numAdults"));
                    t.setNumChildren(doc.getInteger("numChildren"));
                    t.setPrice(doc.getDouble("price"));
                    results.add(t);
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
            return results;
        }
    }
}
