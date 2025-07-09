package com.example.enotes.repository;

import com.example.enotes.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {
    List<ToDo> findByCreatedBy(Integer userId);
}
