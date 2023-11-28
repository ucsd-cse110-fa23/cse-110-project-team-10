package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.MongoDB_Account;
import cse110_project.MongoDB_Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class CreateAccTest {
    String testUser;
    String testPass;
    String URL = "mongodb+srv://chaup070:dir585Muj@cluster0.c8w0sel.mongodb.net/?retryWrites=true&w=majority";
    MongoDB_Test testMongodb;

    @BeforeEach
    public void setup() {
        testUser = "test";
        testPass = "test";
        testMongodb = new MongoDB_Test(URL);
    }

    /**
     * Test for username's availability before create account
     */
    @Test
    public void checkUsernameBeforeCreateTest() throws Exception {
        assertTrue(testMongodb.checkUsername(testUser));
    }

    /**
     * Test for username's availability after create account
     */
    @Test
    public void checkUsernameAfterCreateTest() throws Exception {
        testMongodb.CreateAccount(testUser, testPass);
        assertFalse(testMongodb.checkUsername(testUser));

        testMongodb.Delete(testUser);
    }

    /**
     * Test create account
     */
    @Test
    public void createAccountTest() throws Exception {
        assertTrue(testMongodb.checkUsername(testUser));

        testMongodb.CreateAccount(testUser, testPass);
        assertFalse(testMongodb.checkUsername(testUser));

        testMongodb.Delete(testUser);
    }

}
