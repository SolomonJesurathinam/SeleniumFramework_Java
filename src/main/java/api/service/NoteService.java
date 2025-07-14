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

import java.util.ArrayList;
import java.util.List;

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

    public Response getAllNotes(String token){
        return apiUtils.executeWithTokenRefresh(
                () -> client.getAllNotes(token),
                AuthenticationClient::getToken );
    }

    public List<String> getAllNotes_and_returnNotes(String token, NoteResponse<List<NoteData>> expNotes){
        NoteResponse<List<NoteData>> result = apiUtils.responseToClass(getAllNotes(token), new TypeReference<NoteResponse<List<NoteData>>>() {});
        AssertionUtils.assertMatchingNonNullFieldsSoft(result,expNotes);

        List<NoteData> notes = result.getData();
        int totalNotes = notes.size();
        List<String> ids = new ArrayList<>();
        if(totalNotes > 0){
            for(NoteData nd: notes){
                ids.add(nd.getId());
            }
        }else{
            System.out.println("No notes are present");
        }
        return ids;
    }

    public Response getSingleNote(String token, String id){
        return apiUtils.executeWithTokenRefresh(
                () -> client.getSingleNote(token, id),
                AuthenticationClient::getToken );
    }

    public NoteData getSingleNote_data(String token, String id, NoteResponse<NoteData> expNotes){
        NoteResponse<NoteData> result =  apiUtils.responseToClass(getSingleNote(token, id), new TypeReference<NoteResponse<NoteData>>() {});
        AssertionUtils.assertMatchingNonNullFieldsSoft(result,expNotes);
        return result.getData();
    }

    /*
    NoteResponse<List<NoteData>> result = responseUtil.responseToClass(response, new TypeReference<NoteResponse<List<NoteData>>>() {});
    NoteResponse<Void> result = responseUtil.responseToClass(response, new TypeReference<NoteResponse<Void>>() {});
     */
}
