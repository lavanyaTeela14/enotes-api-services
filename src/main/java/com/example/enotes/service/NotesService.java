package com.example.enotes.service;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
    Boolean saveNotes(String notesDto, MultipartFile file) throws Exception;
    List<NotesDto> getAllNotes();

    NotesResponse getAllNotesByUserId(Integer userId,Integer pageNo,Integer pageSize);

    void softDelete(Integer id) throws Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBinNotes(Integer id);

    void hardDelete(Integer id) throws Exception;

    void deleteRecyclebin(Integer userId);
}
