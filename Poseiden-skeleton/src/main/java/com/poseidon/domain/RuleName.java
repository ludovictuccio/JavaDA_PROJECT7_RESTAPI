package com.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.poseidon.util.Constants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rule_name")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "name")
    private String name;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "description")
    private String description;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "json")
    private String json;

    @Size(max = Constants.SIZE_512, message = Constants.MAX_SIZE_512)
    @Column(name = "template")
    private String template;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "sql_str")
    private String sqlStr;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "sql_part")
    private String sqlPart;

    public RuleName(
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String nameRuleName,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String descriptionRuleName,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String jsonRuleName,
            @Size(max = Constants.SIZE_512, message = Constants.MAX_SIZE_512) final String templateRuleName,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String sqlStrRuleName,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String sqlPartRuleName) {
        super();
        this.name = nameRuleName;
        this.description = descriptionRuleName;
        this.json = jsonRuleName;
        this.template = templateRuleName;
        this.sqlStr = sqlStrRuleName;
        this.sqlPart = sqlPartRuleName;
    }

}
