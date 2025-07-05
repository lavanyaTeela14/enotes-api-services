package com.example.enotes.schedular;

import com.example.enotes.entity.Notes;
import com.example.enotes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {
    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "* * * ? * *")
    public void deleteSchedular()
    {
        LocalDateTime cutOffDate=LocalDateTime.now().minusDays(7);

        List<Notes> deleteNotes=notesRepository.findByIsDeletedAndDeletedOnBefore(true,cutOffDate);
        notesRepository.deleteAll(deleteNotes);
    }
}
