package com.example.enotes.dto;

import com.example.enotes.entity.Notes;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteNotesDto {
    private Integer id;
    private Notes notes;
    private Integer userId;
}
