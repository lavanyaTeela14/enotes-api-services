package com.example.enotes.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesResponse
{
    private List<NotesDto> notes;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isFirst;
    private Boolean isLast;
}
