package com.example.enotes.controller;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.service.NotesService;
import com.example.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/save")
    public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception {
        Boolean savedNotes=notesService.saveNotes(notesDto);
        if(savedNotes)
        {
            return CommonUtil.createBuildResponseMessage("Notes saved succesfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes()
    {
        List<NotesDto> allNotes=notesService.getAllNotes();
        if(ObjectUtils.isEmpty(allNotes))
        {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }
}
