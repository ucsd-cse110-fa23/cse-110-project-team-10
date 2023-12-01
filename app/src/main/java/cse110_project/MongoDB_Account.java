package cse110_project;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;

import java.util.List;

public class MongoDB_Account{
    private String url;

    private MongoDatabase UserAccountDB;
    public MongoCollection<Document> accountsCollection;

    public MongoDB_Account(String url){
        this.url = url;
    }

    //Create user account
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

    //For login
    public boolean LookUpAccount(String username, String password) {
        
        try (MongoClient mongoClient = MongoClients.create(url)) {
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("accounts");
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

    public void updateRecipetoAccount(String user, Document recipe){
        try (MongoClient mongoClient = MongoClients.create(url)){
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("accounts");
            Document account = accountsCollection.find(eq("username", user)).first();
            accountsCollection.updateOne(account, recipe);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void grabRecipeFromAccount(String user, RecipeStateManager state){
        try(MongoClient mongoClient = MongoClients.create(url)){
            UserAccountDB = mongoClient.getDatabase("user_account");
            accountsCollection = UserAccountDB.getCollection("accounts");
            Document account = accountsCollection.find(eq("username", user)).first();
            List<Document> recipes = (List<Document>)account.get("recipes");
            
            if(recipes != null) {
                for(Document recipe : recipes){
                    state = JSONOperations.fromJSONString(recipe.toJson());
                }
            }else{
                state = new RecipeStateManager();
            }

        }
    }
}
