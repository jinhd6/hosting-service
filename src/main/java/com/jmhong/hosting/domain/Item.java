package com.jmhong.hosting.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private String description;
    private Long period;
    private Long price;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
