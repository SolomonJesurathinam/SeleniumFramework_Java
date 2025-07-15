package api.tests;

import api.dto.NoteData;
import api.dto.NoteResponse;
import api.service.NoteService;
import base.BaseAPI;
import org.testng.annotations.Test;

import java.util.List;

public class NoteTests extends BaseAPI {

    NoteService noteService = new NoteService();

    @Test()
    public void createNewNote() {
        NoteData note = NoteData.builder()
                .setTitle("Client Note")
                .setDescription("This is from NoteClient")
                .setCategory("Home")
                .build();

        NoteResponse<NoteData> noteResponse = NoteResponse.<NoteData>builder()
                .setStatus(200)
                .setMessage("Note successfully created")
                .setSuccess(true)
                .setData(note)
                .build();

        System.out.println(noteResponse.toString());

        noteService.createNote_andAssert(note, token, noteResponse);
    }

    @Test
    public void getAllNotes(){
        NoteResponse<List<NoteData>> noteResponse = NoteResponse.<List<NoteData>>builder()
                .setStatus(200)
                .setMessage("Notes successfully retrieved")
                .setSuccess(true)
                .build();
        List<String> noteIds = noteService.getAllNotes_and_returnNotes(token,noteResponse);
        System.out.println(noteIds.toString());
    }

    @Test
    public void getSingleNote(){
        NoteResponse<NoteData> noteResponse = NoteResponse.<NoteData>builder()
                        .setStatus(200)
                        .setMessage("Notes successfully retrieved")
                        .setSuccess(true)
                        .build();

        NoteData data = noteService.getSingleNote_data(token,"6874f2829d1b4b02886570be",noteResponse);
        System.out.println(data.getId());
        System.out.println(data.getCategory());
    }

    public String return_firstNote(){
        NoteResponse<List<NoteData>> getAllNotes_response = NoteResponse.<List<NoteData>>builder()
                .setStatus(200)
                .setMessage("Notes successfully retrieved")
                .setSuccess(true)
                .build();

        return noteService.getFirstNote(token,getAllNotes_response);
    }

    @Test
    public void deleteFirstNote(){
        String first_id = return_firstNote();

        NoteResponse<Void> noteResponse = NoteResponse.<Void>builder()
                        .setSuccess(true)
                        .setStatus(200)
                        .setMessage("Note successfully deleted")
                        .build();

        noteService.deleteSingleNote_assert(token,first_id,noteResponse);
    }

    @Test
    public void updateFirstNote(){

        String first_id = return_firstNote();

        NoteData put_notes = NoteData.builder()
                    .setDescription("New description")
                    .setTitle("Updated one - 1")
                    .setCategory("Work")
                    .setCompleted(false).build();

        NoteResponse<NoteData> noteResponse = NoteResponse.<NoteData>builder()
                .setSuccess(true)
                .setMessage("Note successfully Updated")
                .setStatus(200)
                .setData(put_notes)
                .build();

        System.out.println(noteResponse);
        noteService.updateSingleNote_assert(token, first_id, noteResponse,put_notes);
    }

    @Test
    public void patch_firstNote(){
        String first_id = return_firstNote();

        NoteData data = NoteData.builder().setCompleted(true).build();
        NoteResponse<NoteData> exp = NoteResponse.<NoteData>builder()
                .setStatus(200)
                .setSuccess(true)
                .setMessage("Note successfully Updated")
                .setData(data)
                .build();
        noteService.patchSingleNote_assert(token, first_id,data, exp);
    }

    //create a note, get the note, update the note, patch the complete and delete the note
    @Test
    public void test001(){
        NoteData createNote = NoteData.builder()
                .setTitle("New Notes")
                .setDescription("This is from NoteClient - new one")
                .setCategory("Work")
                .build();

        NoteResponse<NoteData> expCreate = NoteResponse.<NoteData>builder()
                .setStatus(200)
                .setMessage("Note successfully created")
                .setSuccess(true)
                .setData(createNote)
                .build();

        String noteId = noteService.createNote_andAssert(createNote, token, expCreate);

        NoteResponse<NoteData> getNote = NoteResponse.<NoteData>builder()
                .setStatus(200)
                .setMessage("Note successfully retrieved")
                .setSuccess(true)
                .setData(createNote)
                .build();

        NoteData data = noteService.getSingleNote_data(token,noteId,getNote);

        //Update
        NoteData put_notes = NoteData.builder()
                .setDescription("Updting decription")
                .setTitle("Updating Title")
                .setCategory("Personal")
                .setCompleted(false).build();

        NoteResponse<NoteData> noteResponse = NoteResponse.<NoteData>builder()
                .setSuccess(true)
                .setMessage("Note successfully Updated")
                .setStatus(200)
                .setData(put_notes)
                .build();

        noteService.updateSingleNote_assert(token, data.getId(), noteResponse,put_notes);

        //patch
        NoteData patchData = NoteData.builder().setCompleted(true).build();
        NoteResponse<NoteData> exp = NoteResponse.<NoteData>builder()
                .setStatus(200)
                .setSuccess(true)
                .setMessage("Note successfully Updated")
                .setData(patchData)
                .build();
        noteService.patchSingleNote_assert(token, data.getId(),patchData, exp);

        //Delete
        NoteResponse<Void> delete_res = NoteResponse.<Void>builder()
                .setSuccess(true)
                .setStatus(200)
                .setMessage("Note successfully deleted")
                .build();

        noteService.deleteSingleNote_assert(token,data.getId(),delete_res);
    }
}
