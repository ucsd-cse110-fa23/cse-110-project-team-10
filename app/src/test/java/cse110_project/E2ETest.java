package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.MongoDB_Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class E2ETest {
    String testUser;
    String testPass;
    String URL = "mongodb+srv://chaup070:dir585Muj@cluster0.c8w0sel.mongodb.net/?retryWrites=true&w=majority";
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
     * Integration test for create account and login
     */
    @Test
    public void createAccAndLoginTest() throws Exception {
        assertFalse(testMongodb.LookUpAccount(testUser, testPass));

        testMongodb.CreateAccount(testUser, testPass);

        assertTrue(testMongodb.LookUpAccount(testUser, testPass));
    }
}
