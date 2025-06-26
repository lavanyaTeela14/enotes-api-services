package com.example.enotes.service.impl;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.entity.Notes;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.repository.NotesRepository;
import com.example.enotes.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {

        checkCategoryExists(notesDto.getCategory());

        Notes notes=mapper.map(notesDto, Notes.class);
        Notes saveNotes=notesRepository.save(notes);
        if(!ObjectUtils.isEmpty(saveNotes))
        {
            return true;
        }
        return false;
    }

    private void checkCategoryExists(NotesDto.CategoryDto category) throws ResourceNotFoundException {
        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream().map(notes->mapper.map(notes, NotesDto.class)).toList();
    }
}
