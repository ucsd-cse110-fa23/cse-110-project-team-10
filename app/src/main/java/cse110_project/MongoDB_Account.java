package cse110_project;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;


public class MongoDB_Account implements MongoDB{
    private String url;

    private String username;
    private String password;

    private MongoDatabase UserAccountDB;
    public MongoCollection<Document> accountsCollection;

    public MongoDB_Account(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            UserAccountDB = mongoClient.create("user_account");
            accountsCollection = UserAccountDB.getCollection("accounts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void Create() {
        Document account = new Document("_id", new ObjectId());
        account.append("account_id", 10d)
            .append("username", username)
            .append("password", password);

        if(account != null){
            System.out.println(account.toJson());
            accountsCollection.insertOne(account);
            System.out.println("Success");
        }else{
            System.out.println("Failed");
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
