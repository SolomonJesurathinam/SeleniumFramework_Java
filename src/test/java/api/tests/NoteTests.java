package api.tests;

import api.clients.AuthenticationClient;
import api.clients.NoteClient;
import api.dto.Note;
import api.utils.RestAssuredConfiguration;
import config.PropertiesReader;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class NoteTests {

    NoteClient noteClient = new NoteClient();
    String token;

    @BeforeSuite
    public void setupSuite() {
        PropertiesReader propertiesReader = new PropertiesReader();
        String baseUrl = propertiesReader.getProperty("baseUrl");
        RestAssuredConfiguration.setup(baseUrl);
    }

    @BeforeClass
    public void setup() {
        token = AuthenticationClient.getToken();
    }

    @Test()
    public void testCreateNoteUsingClient() {
        Note note = Note.builder()
                .setTitle("Client Note")
                .setDescription("This is from NoteClient")
                .setCategory("Home")
                .build();

        Response response = noteClient.createNote(note,token);
        response.then()
                .statusCode(200);
        assertThat
    }


}
