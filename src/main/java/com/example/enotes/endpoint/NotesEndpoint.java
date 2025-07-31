package com.example.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.enotes.util.Constants.*;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/save")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUserId(@RequestParam(value = "pageNo",defaultValue = PAGE_NO) Integer pageNo,
                                                 @RequestParam(value = "pageSize",defaultValue = PAGE_SIZE) Integer pageSize);

    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUserSearch(@RequestParam(name = "key",defaultValue = PAGE_NO) String keyword
            ,@RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo, @RequestParam(name = PAGE_SIZE,defaultValue = "10") Integer pageSize);


    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
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
