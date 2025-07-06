package com.example.enotes.repository;

import com.example.enotes.entity.FavouriteNotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<FavouriteNotes,Integer> {
    List<FavouriteNotes> findByUserId(Integer userId);
}
