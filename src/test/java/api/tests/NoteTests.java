package api.tests;

import api.dto.Note;
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
        Note note = Note.builder()
                .setTitle("Client Note")
                .setDescription("This is from NoteClient")
                .setCategory("Home")
                .build();

        NoteResponse<NoteData> noteResponse = NoteResponse.<NoteData>builder()
                .setStatus(200)
                .setMessage("Note successfully created")
                .setSuccess(true)
                .setData(NoteData.builder().setTitle("Client Note").build())
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
}
