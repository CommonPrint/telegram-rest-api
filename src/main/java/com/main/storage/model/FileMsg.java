package com.main.storage.model;

import lombok.*;

import javax.persistence.Table;
import java.util.List;

@Data
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMsg {

    private String name;

    private String extension;

    private String type;

    private Long chatId;

    private Long creatorId;

    private List<Long> recipients;

}
