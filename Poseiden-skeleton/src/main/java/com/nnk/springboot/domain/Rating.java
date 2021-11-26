package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
@Data
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String moodys;
    private String sandP;
    private String fitch;
    @Column(name="orderName")
    private Integer order;

    public Rating(String moodys, String sandP, String fitch, int order) {
        this.moodys = moodys;
        this.sandP = sandP;
        this.fitch = fitch;
        this.order = order;
    }
}
