package com.nnk.springboot.domain;

import java.time.LocalDateTime;

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
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id")
    private Integer id;

    // @Column(name = "curve_id")
    private Integer curveId;

    // @Column(name = "as_of_date")
    private LocalDateTime asOfDate;

    // @Column(name = "term")
    private Double term;

    // @Column(name = "value")
    private Double value;

    // @Column(name = "creation_date")
    private LocalDateTime creationDate;

}
