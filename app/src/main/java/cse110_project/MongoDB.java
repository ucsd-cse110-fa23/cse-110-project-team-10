package cse110_project;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

interface MongoDB {
    
    void Delete(MangoCollection<Document> collection);

    void Update(MangoCollection<Document> collection);

    void Read(MangoCollection<Document> collection);
}
