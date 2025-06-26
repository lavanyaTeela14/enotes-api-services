package com.example.enotes.service;

import com.example.enotes.dto.NotesDto;

import java.util.List;

public interface NotesService {
    Boolean saveNotes(NotesDto notesDto) throws Exception;
    List<NotesDto> getAllNotes();
}
