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
    public ResponseEntity<?> saveNotes(@RequestParam String notes,@RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean savedNotes=notesService.saveNotes(notes,file);
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

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUserId(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
        Integer userId=1;
       NotesResponse allNotes=notesService.getAllNotesByUserId(userId,pageNo,pageSize);
        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception
    {
        notesService.softDelete(id);
        return CommonUtil.createBuildResponseMessage("Notes deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception
    {
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes restored successfully",HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getAllUserRecycledNotes()
    {
        Integer userId=1;
        List<NotesDto> notes=notesService.getUserRecycleBinNotes(userId);
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
        Integer userId=1;
        notesService.deleteRecyclebin(userId);
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
