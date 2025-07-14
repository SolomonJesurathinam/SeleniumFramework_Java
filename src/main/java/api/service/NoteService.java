package api.service;

import api.clients.AuthenticationClient;
import api.clients.NoteClient;
import api.dto.Note;
import api.dto.NoteData;
import api.dto.NoteResponse;
import api.utils.ApiUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import utils.AssertionUtils;

public class NoteService {
    private final NoteClient client = new NoteClient();
    ApiUtils apiUtils = new ApiUtils(NoteService.class);

    public Response createNote(Note note, String token){
        return apiUtils.executeWithTokenRefresh(
                ()-> client.createNote(note, token),
                AuthenticationClient::getToken );
    }

    public void createNote_andAssert(Note note, String token, NoteResponse<NoteData> expNoteRes){
        NoteResponse<NoteData> noteResponse = apiUtils.responseToClass(createNote(note,token), new TypeReference<>() {
        });
        System.out.println(noteResponse.toString());
        AssertionUtils.assertMatchingNonNullFieldsSoft(noteResponse,expNoteRes);
    }

}
