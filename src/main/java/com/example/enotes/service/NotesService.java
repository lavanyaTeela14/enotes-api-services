package com.example.enotes.service;

import com.example.enotes.dto.FavouriteNotesDto;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
    Boolean saveNotes(String notesDto, MultipartFile file) throws Exception;
    List<NotesDto> getAllNotes();

    NotesResponse getAllNotesByUserId(Integer pageNo,Integer pageSize);

    NotesResponse getAllNotesByUserSearch(Integer pageNo,Integer pageSize,String keyword);

    void softDelete(Integer id) throws Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBinNotes();

    void hardDelete(Integer id) throws Exception;

    void deleteRecyclebin();

    void favouriteNotes(Integer noteId) throws Exception;
    void unfavouriteNotes(Integer favNoteId) throws Exception;
    List<FavouriteNotesDto> getUserFavouriteNotes();

    Boolean copyNotes(Integer id) throws Exception;
}
