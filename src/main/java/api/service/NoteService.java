package api.service;

import api.clients.AuthenticationClient;
import api.clients.NoteClient;
import api.dto.Note;
import api.dto.NoteData;
import api.dto.NoteResponse;
import api.utils.ApiUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.AssertionUtils;

import java.util.ArrayList;
import java.util.List;

public class NoteService {
    private final NoteClient client = new NoteClient();
    ApiUtils apiUtils = new ApiUtils(NoteService.class);

    public Response createNote(NoteData note, String token){
        return apiUtils.executeWithTokenRefresh(
                ()-> client.createNote(note, token),
                AuthenticationClient::getToken );
    }

    public String createNote_andAssert(NoteData note, String token, NoteResponse<NoteData> expNoteRes){
        NoteResponse<NoteData> noteResponse = apiUtils.responseToClass(createNote(note,token), new TypeReference<>() {
        });
        System.out.println(noteResponse.toString());
        AssertionUtils.assertMatchingNonNullFieldsSoft(noteResponse,expNoteRes);
        return noteResponse.getData().getId();
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

    public Response deleteSingleNote(String token, String id){
        return apiUtils.executeWithTokenRefresh(
                () -> client.deleteSingleNote(token,id),
                AuthenticationClient::getToken );
    }

    public void deleteSingleNote_assert(String token, String id, NoteResponse<Void> expNotes){
        NoteResponse<Void> result =  apiUtils.responseToClass(deleteSingleNote(token, id), new TypeReference<NoteResponse<Void>>() {});
        AssertionUtils.assertMatchingNonNullFieldsSoft(result, expNotes);
    }

    public String getFirstNote(String token, NoteResponse<List<NoteData>> expNotes){
        List<String> ids = getAllNotes_and_returnNotes(token, expNotes);
        if(!ids.isEmpty()){
            return ids.getFirst();
        }else{
            AssertionUtils.assertFail("Notes are not available");
        }
        return null;
    }

    public Response updateSingleNote(String token, String id, NoteData noteData){
        return apiUtils.executeWithTokenRefresh(
                () -> client.updateSingleNote(token, id, noteData),
                AuthenticationClient::getToken );
    }

    public void updateSingleNote_assert(String token, String id, NoteResponse<NoteData> expNotes, NoteData noteData){
        NoteResponse<NoteData> result =  apiUtils.responseToClass(updateSingleNote(token, id, noteData), new TypeReference<NoteResponse<NoteData>>() {});
        AssertionUtils.assertMatchingNonNullFieldsSoft(result, expNotes);
    }

    public Response patchSingleNote(String token, String id, NoteData noteData){
        return apiUtils.executeWithTokenRefresh(
                () -> client.patchSingleNote(token, id, noteData),
                AuthenticationClient::getToken );
    }

    public void patchSingleNote_assert(String token, String id, NoteData noteData, NoteResponse<NoteData> noteRes){
        NoteResponse<NoteData> result =  apiUtils.responseToClass(patchSingleNote(token, id, noteData), new TypeReference<NoteResponse<NoteData>>() {});
        AssertionUtils.assertMatchingNonNullFieldsSoft(result, noteRes);
    }

    /*
    NoteResponse<List<NoteData>> result = responseUtil.responseToClass(response, new TypeReference<NoteResponse<List<NoteData>>>() {});
    NoteResponse<Void> result = responseUtil.responseToClass(response, new TypeReference<NoteResponse<Void>>() {});
     */
}
