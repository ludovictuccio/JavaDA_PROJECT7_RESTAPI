package com.nnk.springboot.domain;

import javax.persistence.Column;
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
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id")
    private Integer id;

    // @Column(name = "moodys_rating")
    private String moodysRating;

    @Column(name = "sandp_rating")
    private String sandPRating;

    // @Column(name = "fitch_rating")
    private String fitchRating;

    // @Column(name = "order_number")
    private Integer orderNumber;

}
