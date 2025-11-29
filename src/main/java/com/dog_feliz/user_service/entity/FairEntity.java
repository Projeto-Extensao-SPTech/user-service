package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fair")
public class FairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fairDate = null;

    @Column(nullable = false)
    private LocalDateTime fairHour = null;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    private Integer interest = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "fair_images", joinColumns = @JoinColumn(name = "fair_id"))
    @Column(name = "image_path")
    private List<String> images = new ArrayList<>();

    public FairEntity() {
    }

    public FairEntity(Long id, LocalDate fairDate, LocalDateTime fairHour, AddressEntity address, Integer interest, List<String> images) {
        this.id = id;
        this.fairDate = fairDate;
        this.fairHour = fairHour;
        this.address = address;
        this.interest = interest;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFairDate() {
        return fairDate;
    }

    public LocalDateTime getFairHour() {
        return fairHour;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public List<String> getImages() {
        return images;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFairDate(LocalDate fairDate) {
        this.fairDate = fairDate;
    }

    public void setFairHour(LocalDateTime fairHour) {
        this.fairHour = fairHour;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "FairEntity{" +
                "id=" + id +
                ", fairDate=" + fairDate +
                ", fairHour=" + fairHour +
                ", address=" + address +
                ", image=" + images +
                '}';
    }
}
