package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "file")
@Data
public class projectFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_url")
    private String fileUrl;
}