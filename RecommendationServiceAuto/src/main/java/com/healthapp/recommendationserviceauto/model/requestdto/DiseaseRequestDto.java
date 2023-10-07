package com.healthapp.recommendationserviceauto.model.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DiseaseRequestDto {
    private String diseaseName;
    private String note;
}
