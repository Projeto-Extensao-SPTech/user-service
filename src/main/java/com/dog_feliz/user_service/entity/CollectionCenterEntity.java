package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "collection_center_tb")
public class CollectionCenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    public CollectionCenterEntity() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressEntity getAddress() {
        return address;
    }
}