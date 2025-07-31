package com.example.enotes.controller;

import com.example.enotes.dto.FavouriteNotesDto;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesResponse;
import com.example.enotes.endpoint.NotesEndpoint;
import com.example.enotes.entity.FavouriteNotes;
import com.example.enotes.service.NotesService;
import com.example.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesController implements NotesEndpoint {
    @Autowired
    private NotesService notesService;

    @Override
    public ResponseEntity<?> saveNotes(String notes,MultipartFile file) throws Exception {
        Boolean savedNotes=notesService.saveNotes(notes,file);
        if(savedNotes)
        {
            return CommonUtil.createBuildResponseMessage("Notes saved succesfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllNotes()
    {
        List<NotesDto> allNotes=notesService.getAllNotes();
        if(ObjectUtils.isEmpty(allNotes))
        {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUserId(Integer pageNo,
                                                 Integer pageSize)
    {
       NotesResponse allNotes=notesService.getAllNotesByUserId(pageNo,pageSize);
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUserSearch(String keyword,Integer pageNo,Integer pageSize)
    {
        NotesResponse allNotes=notesService.getAllNotesByUserSearch(pageNo,pageSize,keyword);
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNotes(Integer id) throws Exception
    {
        notesService.softDelete(id);
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(Integer id) throws Exception
    {
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes restored successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllUserRecycledNotes()
    {
        List<NotesDto> notes=notesService.getUserRecycleBinNotes();
        if(ObjectUtils.isEmpty(notes))
        {
            return CommonUtil.createBuildResponseMessage("Notes not available in recycle bin!",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> HardDeleteNotes(Integer id) throws Exception
    {
        notesService.hardDelete(id);
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> DeleteRecyclebin() throws Exception
    {
        notesService.deleteRecyclebin();
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> favouriteNotes(Integer noteId) throws Exception
    {
        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("NOtes added to Fav",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> unfavouriteNotes(Integer favId) throws Exception
    {
        notesService.unfavouriteNotes(favId);
        return CommonUtil.createBuildResponseMessage("Removed from favouites",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserFavouriteNotes()
    {

        List<FavouriteNotesDto> notes=notesService.getUserFavouriteNotes();
        if(ObjectUtils.isEmpty(notes))
        {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> copyNotes(Integer id) throws Exception
    {
        Boolean copied=notesService.copyNotes(id);
        if(copied)
        {
            return CommonUtil.createBuildResponseMessage("Notes copied successfully",HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not copied",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
