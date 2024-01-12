package ch.bbb.zooappbackend;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class DBConnector {
    private static final String CONNECTION_STRING = "mongodb://root:root@localhost/";
    private static final String DATABASE_NAME = "zoo_RestAPI";
    private static final String COLLECTION_NAME = "tickets";

    public static Ticket writeTicket(Document doc) {
        Ticket t = new Ticket();
        t.setId(doc.getInteger("id"));
        t.setDate(doc.getString("date"));
        t.setNumAdults(doc.getInteger("numAdults"));
        t.setNumChildren(doc.getInteger("numChildren"));
        t.setPrice(doc.getDouble("price"));
        return t;
    }

    public static Document toDocument(Ticket t) {
        Document document = new Document();
        document.append("id", t.getId());
        document.append("date", t.getDate());
        document.append("numAdults", t.getNumAdults());
        document.append("numChildren", t.getNumChildren());
        document.append("price", t.getPrice());
        return document;
    }

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
                    results.add(writeTicket(doc));
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
            return results;
        }
    }

    public static Ticket getTicketById(int ticketId) {

        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                MongoCollection<Document> ticketDocs = database.getCollection(COLLECTION_NAME);
                Document query = new Document("id", ticketId);
                FindIterable<Document> iterDoc = ticketDocs.find(query);

                Document doc = iterDoc.first();

                if (doc != null) {
                    return writeTicket(doc);
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
            return null;
        }
    }

    public static void addTicket(Ticket ticket) {

        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                MongoCollection<Document> ticketDocs = database.getCollection(COLLECTION_NAME);
                Document ticketDoc = toDocument(ticket);
                ticketDocs.insertOne(ticketDoc);
                System.out.println("Ticket added successfully!");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    public static void updateTicket(Ticket updatedTicket) {
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                MongoCollection<Document> ticketDocs = database.getCollection(COLLECTION_NAME);

                // Create a query to find the document with the specified id
                Document query = new Document("id", updatedTicket.getId());

                // Create an update with the new values
                Document updateDoc = new Document();
                updateDoc.append("$set", new Document()
                        .append("date", updatedTicket.getDate())
                        .append("numAdults", updatedTicket.getNumAdults())
                        .append("numChildren", updatedTicket.getNumChildren())
                        .append("price", updatedTicket.getPrice()));

                // Perform the update
                ticketDocs.updateOne(query, updateDoc, new UpdateOptions().upsert(true));

                System.out.println("Ticket updated successfully!");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    public static void updateTicketFields(Ticket updatedTicket) {
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                MongoCollection<Document> ticketDocs = database.getCollection(COLLECTION_NAME);

                // Create a query to find the document with the specified id
                Document query = new Document("id", updatedTicket.getId());

                // Create an update with the new values
                Document updateDoc = new Document();
                if (updatedTicket.getDate() != null) {
                    updateDoc.append("$set", new Document("date", updatedTicket.getDate()));
                }
                if (updatedTicket.getNumAdults() != 0) {
                    updateDoc.append("$set", new Document("numAdults", updatedTicket.getNumAdults()));
                }
                if (updatedTicket.getNumChildren() != 0) {
                    updateDoc.append("$set", new Document("numChildren", updatedTicket.getNumChildren()));
                }

                updateDoc.append("$set", new Document("price", updatedTicket.getPrice()));

                // Perform the update
                ticketDocs.updateOne(query, updateDoc, new UpdateOptions().upsert(true));

                System.out.println("Ticket fields updated successfully!");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    public static boolean deleteTicket(int ticketId) {
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                MongoCollection<Document> ticketDocs = database.getCollection(COLLECTION_NAME);

                // Create a filter to find the document with the specified id
                Bson filter = Filters.eq("id", ticketId);

                // Perform the deletion
                DeleteResult deleteResult = ticketDocs.deleteOne(filter);

                // Check if a document was deleted
                return deleteResult.getDeletedCount() > 0;
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
                return false;
            }
        }
    }
}
