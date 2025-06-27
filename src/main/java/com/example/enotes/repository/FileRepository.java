package com.example.enotes.repository;

import com.example.enotes.entity.FileDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
