package cse110_project;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;

public class MongoDB_Account implements MongoDB{
    private String url;

    private MongoDatabase UserAccountDB;
    public MongoCollection<Document> accountsCollection;

    public MongoDB_Account(String url){
        this.url = url;
    }

    //Create user account
    @Override
    public void CreateAccount(String username, String password) {
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

    //delete recipe
    @Override
    public void Delete(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    //Update recipe
    @Override
    public void Update(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }


    //For login
    @Override
    public void LookUpAccount(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Read'");
    }

    public boolean checkUsername(String username) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("accounts");
            Document account = accountsCollection.find(eq("username",username)).first();
            
            if(account == null){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
