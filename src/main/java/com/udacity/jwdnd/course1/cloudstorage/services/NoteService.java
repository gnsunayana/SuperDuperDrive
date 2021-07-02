package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper,UserService userService){
       this.noteMapper= noteMapper;
       this.userService= userService;
    }

    @PostConstruct
    public void postConstruct(){
           System.out.println("NoteService has been created");
    }


    public boolean isNoteValid(Note note,String userName){
        if(note.getNoteTitle() == null || note.getNoteTitle().isBlank()){
            return false;
        }
        int userId = userService.getUser(userName).getUserId();
        return !noteMapper.existsByTitleWithAnotherId(note.getNoteTitle(),note.getNoteId(),userId);
    }

    //Create otherwise update the existing Note
    public int saveNote(final Note note,final String userName)throws IOException {
        System.out.println("In NoteService save method");
        int userId= userService.getUser(userName).getUserId();
        note.setUserId(userId);

        int noteId = 0;
        if(note.getNoteId() == null){
            noteId= noteMapper.addNote(note);
            System.out.println("Note inserted into database with noteID"+ noteId);
        }
        else{
            noteId= noteMapper.updateNote(note);
            System.out.println("Note updated in database with noteId"+noteId);
        }

        if(noteId < 1){
            throw new IOException("Failed to insert/update note into database");
        }
        return noteId;
    }


    public void deleteNote(final Integer noteId,final String userName){
        System.out.println("In NoteService delete method");
        int userId= userService.getUser(userName).getUserId();
        noteMapper.deleteNote(noteId,userId);
    }

   public List<Note> listNotes(String userName){
        System.out.println("In NoteService list method");
        int userId = userService.getUser(userName).getUserId();
        List<Note> notesList= noteMapper.listByUser(userId);
        for(Note note:notesList){
            System.out.printf("noteList id: %d title:%s\n", note.getNoteId(), note.getNoteTitle());
        }
        return notesList;
   }

}
