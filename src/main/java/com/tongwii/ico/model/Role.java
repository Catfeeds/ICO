package com.tongwii.ico.model;

import javax.persistence.*;

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_name_code")
    private String roleNameCode;

    private String description;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return role_name_code
     */
    public String getRoleNameCode() {
        return roleNameCode;
    }

    /**
     * @param roleNameCode
     */
    public void setRoleNameCode(String roleNameCode) {
        this.roleNameCode = roleNameCode;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

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