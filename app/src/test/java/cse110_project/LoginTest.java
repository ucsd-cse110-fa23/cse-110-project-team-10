package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.MongoDB_Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class LoginTest {
    String testUser;
    String testPass;
    String URL = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";
    MongoDB_Test testMongodb;

    @BeforeEach
    public void setup() {
        testUser = "test";
        testPass = "test";
        testMongodb = new MongoDB_Test(URL);

        //if account exists, delete before testing
        testMongodb.Delete(testUser);
    }

    /**
     * Test login with a nonexisting account
     */
    @Test
    public void loginInvalidAccTest() throws Exception {
        assertFalse(testMongodb.LookUpAccount(testUser, testPass));
    }

    /**
     * Test login with wrong username
     */
    @Test
    public void loginWrongUsernameTest() throws Exception {
        testMongodb.CreateAccount(testUser, testPass);
        assertFalse(testMongodb.LookUpAccount("wronguser", testPass));
    }

    /**
     * Test login with wrong password
     */
    @Test
    public void loginWrongPasswordTest() throws Exception {
        testMongodb.CreateAccount(testUser, testPass);
        assertFalse(testMongodb.LookUpAccount(testUser, "wrongpass"));
    }

    /**
     * Test login with a valid account
     */
    @Test
    public void loginValidAccTest() throws Exception {
        testMongodb.CreateAccount(testUser, testPass);
        assertTrue(testMongodb.LookUpAccount(testUser, testPass));
    }

}