package com.app.epothon.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScannerModel {
    @Id
    private String id;
    private String imageFile;
    private String audioFile;
    private String language;

    @Column(columnDefinition="TEXT")
    private String textFile;
}
