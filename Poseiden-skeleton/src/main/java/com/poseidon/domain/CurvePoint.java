package com.poseidon.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poseidon.util.Constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @PositiveOrZero
    @Column(name = "curve_id")
    private Integer curveId;

    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @DateTimeFormat(pattern = Constants.DATE_PATTERN)
    @Column(name = "as_of_date")
    private LocalDateTime asOfDate; // use while update process

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @DateTimeFormat(pattern = Constants.DATE_PATTERN)
    @Column(name = "creation_date")
    private LocalDateTime creationDate; // use while create a new curvepoint
                                        // process

//    /**
//     * @param id
//     * @param curveId
//     * @param term
//     * @param value
//     */
//    public CurvePoint(Integer id, @PositiveOrZero Integer curveId, Double term,
//            Double value) {
//        super();
//        this.id = id;
//        this.curveId = curveId;
//        this.term = term;
//        this.value = value;
//    }

}
