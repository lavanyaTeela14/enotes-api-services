package com.example.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUserId(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize);

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUserSearch(@RequestParam(name = "key",defaultValue = "") String keyword
            ,@RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo, @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize);


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllUserRecycledNotes();

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> HardDeleteNotes(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/delete")
    public ResponseEntity<?> DeleteRecyclebin() throws Exception;

    @GetMapping("/fav/{noteId}")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception;

    @DeleteMapping("/unfav/{favId}")
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favId) throws Exception;

    @GetMapping("/fav-notes")
    public ResponseEntity<?> getUserFavouriteNotes();

    @GetMapping("/copy/{id}")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
