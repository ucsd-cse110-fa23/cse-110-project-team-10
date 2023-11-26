package cse110_project;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;


public class MongoDB_Account implements MongoDB{
    private String url;

    private MongoDatabase UserAccountDB;
    public MongoCollection<Document> accountsCollection;

    public MongoDB_Account(String url){
        this.url = url;
    }
    @Override
    public void Create(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("accounts");
            Document account = new Document("_id", new ObjectId());
            account.append("account_id", 10d)
                .append("username", username)
                .append("password", password);
            
            accountsCollection.insertOne(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public void Read() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Read'");
    }
    
}
