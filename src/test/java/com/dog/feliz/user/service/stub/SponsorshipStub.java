package com.dog.feliz.user.service.stub;

import com.dog.feliz.user.service.controller.dto.SponsorshipRequestDto;

public final class SponsorshipStub {

    private SponsorshipStub() {
    }

    public static SponsorshipRequestDto validRequest(Long sponsorId) {
        SponsorshipRequestDto dto = new SponsorshipRequestDto();
        dto.setSponsorId(sponsorId);
        dto.setType("Financeiro");
        dto.setDescription("Apoio mensal ao abrigo");
        dto.setDepartment("Marketing");
        return dto;
    }
}
