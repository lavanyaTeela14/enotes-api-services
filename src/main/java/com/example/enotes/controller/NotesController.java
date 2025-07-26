package com.example.enotes.controller;

import com.example.enotes.dto.FavouriteNotesDto;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesResponse;
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
@RequestMapping("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,@RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean savedNotes=notesService.saveNotes(notes,file);
        if(savedNotes)
        {
            return CommonUtil.createBuildResponseMessage("Notes saved succesfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes()
    {
        List<NotesDto> allNotes=notesService.getAllNotes();
        if(ObjectUtils.isEmpty(allNotes))
        {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUserId(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
       NotesResponse allNotes=notesService.getAllNotesByUserId(pageNo,pageSize);
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception
    {
        notesService.softDelete(id);
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception
    {
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes restored successfully",HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllUserRecycledNotes()
    {
        List<NotesDto> notes=notesService.getUserRecycleBinNotes();
        if(ObjectUtils.isEmpty(notes))
        {
            return CommonUtil.createBuildResponseMessage("Notes not available in recycle bin!",HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> HardDeleteNotes(@PathVariable Integer id) throws Exception
    {
        notesService.hardDelete(id);
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> DeleteRecyclebin() throws Exception
    {
        notesService.deleteRecyclebin();
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/fav/{noteId}")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception
    {
        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("NOtes added to Fav",HttpStatus.CREATED);
    }

    @DeleteMapping("/unfav/{favId}")
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favId) throws Exception
    {
        notesService.unfavouriteNotes(favId);
        return CommonUtil.createBuildResponseMessage("Removed from favouites",HttpStatus.OK);
    }

    @GetMapping("/fav-notes")
    public ResponseEntity<?> getUserFavouriteNotes()
    {

        List<FavouriteNotesDto> notes=notesService.getUserFavouriteNotes();
        if(ObjectUtils.isEmpty(notes))
        {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/copy/{id}")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception
    {
        Boolean copied=notesService.copyNotes(id);
        if(copied)
        {
            return CommonUtil.createBuildResponseMessage("Notes copied successfully",HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not copied",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
