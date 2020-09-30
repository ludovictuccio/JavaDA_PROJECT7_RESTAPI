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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Min(value = -128) // tynint(4)
    @Max(value = 127)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 125)
    @Column(name = "moodys_rating")
    private String moodysRating;

    @Size(max = 125)
    @Column(name = "sandp_rating")
    private String sandPRating;

    @Size(max = 125)
    @Column(name = "fitch_rating")
    private String fitchRating;

    @Max(value = 127) // tynint
    @Column(name = "order_number")
    private Integer orderNumber;

    /**
     * @param moodysRating
     * @param sandPRating
     * @param fitchRating
     * @param orderNumber
     */
    public Rating(@Size(max = 125) String moodysRating,
            @Size(max = 125) String sandPRating,
            @Size(max = 125) String fitchRating,
            @Max(127) Integer orderNumber) {
        super();
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

}
