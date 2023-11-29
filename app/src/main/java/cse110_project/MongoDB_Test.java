package cse110_project;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import static com.mongodb.client.model.Filters.*;

public class MongoDB_Test{

    private String url;

    private MongoDatabase UserAccountDB;
    public MongoCollection<Document> accountsCollection;

    public MongoDB_Test(String url){
        this.url = url;
    }

    public void CreateAccount(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("test");
            Document account = new Document("_id", new ObjectId());
            account.append("account_id", 10d)
                .append("username", username)
                .append("password", password);
            
            accountsCollection.insertOne(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Delete(String username) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("test");

            Bson filter = eq("username", username);
            DeleteResult delAcc = accountsCollection.deleteOne(filter);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean LookUpAccount(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("test");
            Document account = accountsCollection.find(eq("username",username)).first();
            
            if(account == null){
                return false;
            }
            else{
                if(password.equals(account.getString("password"))){
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUsername(String username) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("test");
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