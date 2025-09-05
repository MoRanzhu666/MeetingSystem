package com.gp.mts.bean.po;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorys")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "describe_info", nullable = false)
    private String describeInfo;
}
