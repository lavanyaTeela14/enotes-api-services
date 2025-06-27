package com.example.enotes.service.impl;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.entity.FileDetails;
import com.example.enotes.entity.Notes;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.repository.FileRepository;
import com.example.enotes.repository.NotesRepository;
import com.example.enotes.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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

        checkCategoryExists(notesDto.getCategory());
        Notes notesMap=mapper.map(notesDto, Notes.class);

        FileDetails fileDetails=saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDetails))
        {
            notesMap.setFileDetails(fileDetails);
        }else {
            notesMap.setFileDetails(null);
        }

        Notes saveNotes=notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(saveNotes))
        {
            return true;
        }
        return false;
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
}
