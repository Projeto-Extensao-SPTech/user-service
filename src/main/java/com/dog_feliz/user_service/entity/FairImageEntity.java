package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;


@Entity
@Table(name ="feira_imagem")
public class FairImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    @ManyToOne
    private FairEntity fair;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public FairEntity getFair() {
        return fair;
    }

    public void setFair(FairEntity fair) {
        this.fair = fair;
    }
}
