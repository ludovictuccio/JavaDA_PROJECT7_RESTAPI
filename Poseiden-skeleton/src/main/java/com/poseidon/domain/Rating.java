package com.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.poseidon.util.Constants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "moodys_rating")
    private String moodysRating;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "sandp_rating")
    private String sandPRating;

    @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125)
    @Column(name = "fitch_rating")
    private String fitchRating;

    @PositiveOrZero(message = "Order number must be a positive number (or equal to 0) not already used.")
    @Column(name = "order_number", unique = true)
    private Integer orderNumber;

    public Rating(
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String moodys,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String sandp,
            @Size(max = Constants.SIZE_125, message = Constants.MAX_SIZE_125) final String fitch,
            Integer ordernumb) {
        super();
        this.moodysRating = moodys;
        this.sandPRating = sandp;
        this.fitchRating = fitch;
        this.orderNumber = ordernumb;
    }

}
