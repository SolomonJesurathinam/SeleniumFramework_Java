package api.clients;

import api.constants.ApiEndpoints;
import api.dto.Note;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class NoteClient {

    public Response createNote(Note note, String token){
        return given()
                .body(note)
                .header("x-auth-token", token)
                .when()
                .post(ApiEndpoints.CREATE_NEW_NOTE.getPath());
    }

    public Response getAllNotes(String token){
        return given()
                .header("x-auth-token", token)
                .when()
                .get(ApiEndpoints.GET_ALL_NOTES.getPath());
    }

    public Response getSingleNote(String token, String id){
        return given()
                .header("x-auth-token", token)
                .when()
                .get(ApiEndpoints.GET_SINGLE_NOTE.getPath(id));
    }

}
