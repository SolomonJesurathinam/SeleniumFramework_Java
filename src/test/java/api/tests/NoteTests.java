package api.tests;

import api.dto.Note;
import api.dto.NoteData;
import api.dto.NoteResponse;
import api.service.NoteService;
import base.BaseAPI;
import org.testng.annotations.Test;

public class NoteTests extends BaseAPI {

    NoteService noteService = new NoteService();

    @Test()
    public void createNewNote() {
        Note note = Note.builder()
                .setTitle("Client Note")
                .setDescription("This is from NoteClient")
                .setCategory("Home")
                .build();

        NoteResponse noteResponse = NoteResponse.builder()
                .setStatus(200)
                .setMessage("Note successfully created")
                .setData(NoteData.builder().setTitle("Client Note").build())
                .build();

        noteService.createNote_andAssert(note, token, noteResponse);
    }


}
