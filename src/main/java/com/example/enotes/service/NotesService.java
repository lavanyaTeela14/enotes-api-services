package com.example.enotes.service;

import com.example.enotes.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
    Boolean saveNotes(String notesDto, MultipartFile file) throws Exception;
    List<NotesDto> getAllNotes();
}
