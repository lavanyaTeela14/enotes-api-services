package com.example.enotes.service.impl;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesResponse;
import com.example.enotes.entity.FileDetails;
import com.example.enotes.entity.Notes;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.repository.FileRepository;
import com.example.enotes.repository.NotesRepository;
import com.example.enotes.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper obj=new ObjectMapper();
        NotesDto notesDto=obj.readValue(notes, NotesDto.class);
        notesDto.setIsDeleted(false);
        notesDto.setDeletedOn(null);

        if(!ObjectUtils.isEmpty(notesDto.getId()))
        {
            updateNotes(notesDto,file);
        }

        checkCategoryExists(notesDto.getCategory());
        Notes notesMap=mapper.map(notesDto, Notes.class);

        FileDetails fileDetails=saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDetails))
        {
            notesMap.setFileDetails(fileDetails);
        }else {
            if(ObjectUtils.isEmpty(notesDto.getId()))
            {
                notesMap.setFileDetails(null);
            }
        }

        Notes saveNotes=notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(saveNotes))
        {
            return true;
        }
        return false;
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception{
        Notes existNotes=notesRepository.findById(notesDto.getId()).orElseThrow(()->new ResourceNotFoundException("Invalid Notes Id!!"));
        if(ObjectUtils.isEmpty(file))
        {
            notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), NotesDto.FilesDto.class));
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if(!ObjectUtils.isEmpty(file) && !file.isEmpty())
        {
            String originalFileName= file.getOriginalFilename();
            String extension= FilenameUtils.getExtension(originalFileName);

            List<String> allowExtensions= Arrays.asList("pdf","doc","jpg","png");
            if(!allowExtensions.contains(extension))
            {
                throw new IllegalArgumentException("Invalid file format!!, Upload only .pdf, .doc, .jpg, .png");
            }
            String randomString= UUID.randomUUID().toString();
            String uploadFileName=randomString+"."+extension;

            File saveFile=new File(uploadPath);
            if(!saveFile.exists())
            {
                saveFile.mkdir();
            }
            String storePath=uploadPath.concat(uploadFileName);
            long upload =Files.copy(file.getInputStream(), Paths.get(storePath));
            if(upload!=0)
            {
                FileDetails fileDetails=new FileDetails();

                fileDetails.setOriginalFileName(originalFileName);
                fileDetails.setDisplayFileName(getDisplayName(originalFileName));
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(storePath);

                FileDetails saveFileDetails=fileRepository.save(fileDetails);
                return saveFileDetails;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        String extension= FilenameUtils.getExtension(originalFileName);
        String fileName=FilenameUtils.removeExtension(originalFileName);
        if(fileName.length()>8)
        {
            fileName = fileName.substring(0,7);
        }
        fileName= fileName + "." + extension;
        return fileName;
    }

    private void checkCategoryExists(NotesDto.CategoryDto category) throws ResourceNotFoundException {
        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream().map(notes->mapper.map(notes, NotesDto.class)).toList();
    }

    @Override
    public NotesResponse getAllNotesByUserId(Integer userId, Integer pageNo, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Notes> pageNotes= notesRepository.findByCreatedByAndIsDeletedFalse(userId,pageable);
        List<NotesDto> notes=pageNotes.get().map(n->mapper.map(n,NotesDto.class)).toList();
        NotesResponse response=NotesResponse.builder()
                .notes(notes)
                .pageSize(pageNotes.getSize())
                .pageNo(pageNotes.getNumber())
                .totalElements(pageNotes.getNumberOfElements())
                .totalPages(pageNotes.getTotalPages())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();
        return response;
    }

    @Override
    public void softDelete(Integer id) throws Exception {
        Notes notes=notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Notes Id!!"));
        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepository.save(notes);
    }

    @Override
    public void restoreNotes(Integer id) throws Exception {
        Notes notes=notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Notes Id!!"));
        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepository.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer id) {
        List<Notes> notes=notesRepository.findByCreatedByAndIsDeletedTrue(id);
        List<NotesDto> notesDtoList =notes.stream().map(note->mapper.map(note,NotesDto.class)).toList();
        return notesDtoList;
    }

    @Override
    public void hardDelete(Integer id) throws Exception{
        Notes notes=notesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Notes not available to delete"));
        if(notes.getIsDeleted())
        {
            notesRepository.delete(notes);
        }
        else{
            throw new IllegalArgumentException("Hard delete cannot be done!!");
        }
    }

    @Override
    public void deleteRecyclebin(Integer userId) {
        List<Notes> notes=notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        if(!ObjectUtils.isEmpty(notes))
        {
            notesRepository.deleteAll(notes);
        }
    }
}
