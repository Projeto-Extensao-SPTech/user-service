package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "feira")
public class FairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fairDate;

    @Column(nullable = false)
    private LocalDateTime fairHour;

    @Column(nullable = false)
    private AddressRequestDto address;

    @OneToMany(mappedBy = "fair", cascade = CascadeType.ALL)
    private List<FairImageEntity> images;

    public FairEntity() {
    }

    public FairEntity(Long id, LocalDate fairDate, LocalDateTime fairHour, AddressRequestDto address, List<FairImageEntity> images) {
        this.id = id;
        this.fairDate = fairDate;
        this.fairHour = fairHour;
        this.address = address;
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

    public AddressRequestDto getAddress() {
        return address;
    }

    public List<FairImageEntity> getImage() {
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

    public void setAddress(AddressRequestDto address) {
        this.address = address;
    }

    public void setImages(List<FairImageEntity> images) {
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
