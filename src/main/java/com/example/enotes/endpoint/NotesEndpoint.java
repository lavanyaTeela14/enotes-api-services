package com.example.enotes.endpoint;

import com.example.enotes.dto.NotesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.enotes.util.Constants.*;

@Tag(name = "Notes",description = "All Notes API's")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(summary = "Save user notes",tags = {"Notes","User"},description = "User save notes")
    @PostMapping(value = "/save",consumes = "multipart/form-data")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> saveNotes(@RequestParam @Parameter(description = "Json string notes",required = true,content = @Content(schema = @Schema(implementation = NotesRequest.class))) String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(summary = "Get all user notes",tags = {"Notes"},description = "Get all save notes Admin")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();

    @Operation(summary = "Get all notes by ID",tags = {"Notes","User"},description = "Get all save notes User")
    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUserId(@RequestParam(value = "pageNo",defaultValue = PAGE_NO) Integer pageNo,
                                                 @RequestParam(value = "pageSize",defaultValue = PAGE_SIZE) Integer pageSize);

    @Operation(summary = "Search notes",tags = {"Notes","User"},description = "User search notes")
    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUserSearch(@RequestParam(name = "key",defaultValue = PAGE_NO) String keyword
            ,@RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo, @RequestParam(name = PAGE_SIZE,defaultValue = "10") Integer pageSize);

    @Operation(summary = "Delete user notes",tags = {"Notes","User"},description = "User delete notes")
    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Restore notes",tags = {"Notes","User"},description = "User Restore notes")
    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Recycle bin notes",tags = {"Notes","User"},description = "Recycle bin notes")
    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllUserRecycledNotes();

    @Operation(summary = "delete hard user notes",tags = {"Notes","User"},description = "User hard delete notes")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> HardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "delete user recyclebin",tags = {"Notes","User"},description = "Delete user recycle bin")
    @DeleteMapping("/delete")
    public ResponseEntity<?> DeleteRecyclebin() throws Exception;

    @Operation(summary = "user favourite notes",tags = {"Notes","User"},description = "User favourite notes")
    @GetMapping("/fav/{noteId}")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception;

    @Operation(summary = "Unfav user notes",tags = {"Notes","User"},description = "User Unfav notes")
    @DeleteMapping("/unfav/{favId}")
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favId) throws Exception;

    @Operation(summary = "get user fav notes",tags = {"Notes","User"},description = "get User fav notes")
    @GetMapping("/fav-notes")
    public ResponseEntity<?> getUserFavouriteNotes();

    @Operation(summary = "copy user notes",tags = {"Notes","User"},description = "User copy notes")
    @GetMapping("/copy/{id}")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
