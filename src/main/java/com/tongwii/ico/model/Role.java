package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_name_code")
    private String roleNameCode;

    private String description;

    public enum RoleCode {
        ADMIN("ROLE_ADMIN", "管理员", 1),
        USER("ROLE_USER", "用户", 2);

        private String code;
        private String roleName;
        private Integer id;

        public String getCode() {
            return code;
        }

        public String getRoleName() {
            return roleName;
        }

        public Integer getId() {
            return id;
        }

        RoleCode(String code, String roleName, Integer id) {
            this.code = code;
            this.roleName = roleName;
            this.id = id;
        }
    }
}