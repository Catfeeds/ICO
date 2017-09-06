package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "user_role_relation")
@Data
public class UserRoleRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}