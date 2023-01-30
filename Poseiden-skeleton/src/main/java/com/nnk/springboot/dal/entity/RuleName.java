package com.nnk.springboot.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Column
    @NotBlank(message = "Json is mandatory")
    private String json;

    @Column
    @NotBlank(message = "Template is mandatory")
    private String template;

    @Column(name = "sql_str")
    @NotBlank(message = "SQL Str is mandatory")
    private String sqlStr;

    @Column(name = "sql_part")
    @NotBlank(message = "SQL part is mandatory")
    private String sqlPart;

    public RuleName (String nameIn,
                     String descriptionIn,
                     String jsonIn,
                     String templateIn,
                     String sqlStrIn,
                     String sqlPartIn) {
        this.setName(nameIn);
        this.setDescription(descriptionIn);
        this.setJson(jsonIn);
        this.setTemplate(templateIn);
        this.setSqlStr(sqlStrIn);
        this.setSqlPart(sqlPartIn);
    }

    public RuleName() {

    }
}
