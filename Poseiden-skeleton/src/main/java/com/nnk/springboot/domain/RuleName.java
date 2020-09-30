package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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

    @Min(value = -128) // tynint(4)
    @Max(value = 127)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 125)
    @Column(name = "name")
    private String name;

    @Size(max = 125)
    @Column(name = "description")
    private String description;

    @Size(max = 125)
    @Column(name = "json")
    private String json;

    @Size(max = 512)
    @Column(name = "template")
    private String template;

    @Size(max = 125)
    @Column(name = "sql_str")
    private String sqlStr;

    @Size(max = 125)
    @Column(name = "sql_part")
    private String sqlPart;

    /**
     * @param name
     * @param description
     * @param json
     * @param template
     * @param sqlStr
     * @param sqlPart
     */
    public RuleName(@Size(max = 125) String name,
            @Size(max = 125) String description, @Size(max = 125) String json,
            @Size(max = 512) String template, @Size(max = 125) String sqlStr,
            @Size(max = 125) String sqlPart) {
        super();
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

}
