package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "project_user_relation")
@Data
public class ProjectUserRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "project_id")
    private Integer projectId;
}