package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rule_name")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id")
    private Integer id;

    // @Column(name = "name")
    private String name;

    // @Column(name = "description")
    private String description;

    // @Column(name = "json")
    private String json;

    // @Column(name = "template")
    private String template;

    // @Column(name = "sql_str")
    private String sqlStr;

    // @Column(name = "sql_part")
    private String sqlPart;

}
