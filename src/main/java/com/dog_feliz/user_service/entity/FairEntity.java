package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.controller.dto.FairDto;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

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

    private List<MultipartFile> image;

    public FairEntity(FairDto dto) {
        this.fairDate = dto.getFairDate();
        this.fairHour = dto.getFairHour();
        this.address = dto.getAddress();
        this.image = dto.getImage();
    }

    public FairEntity(Long id, FairDto dto) {
        this(dto);
        this.id = id;
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

    public List<MultipartFile> getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "FairEntity{" +
                "id=" + id +
                ", fairDate=" + fairDate +
                ", fairHour=" + fairHour +
                ", address=" + address +
                ", image=" + image +
                '}';
    }
}
