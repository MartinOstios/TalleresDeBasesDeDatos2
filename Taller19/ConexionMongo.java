package com.uam;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
//import static com.mongodb.client.model.Filters.eq;
//import static com.mongodb.client.model.Filters.gt;
//import static com.mongodb.client.model.Filters.and;
//import static com.mongodb.client.model.Updates.set;
//import static com.mongodb.client.model.Sorts.ascending;
import com.mongodb.client.MongoClients;

public class ConexionMongo {
    private static ConexionMongo instance;
    private String uri;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    private ConexionMongo() {
        this.uri = "mongodb://localhost:27017";
        this.mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("taller");
        this.collection = database.getCollection("usuarios");
        System.out.println("Conexión MongoDB exitosa");
    }

    public static ConexionMongo getInstance() {
        if (instance == null) {
            instance = new ConexionMongo();
        }
        return instance;
    }

    public static MongoCollection<Document> getCollection() {
        return getInstance().collection;
    }

    public static MongoCollection<Document> setCollection(String collectionName) {
        getInstance().collection = getInstance().database.getCollection(collectionName);
        return getInstance().collection;
    }
}
