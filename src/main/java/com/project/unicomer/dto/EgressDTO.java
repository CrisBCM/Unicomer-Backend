package com.project.unicomer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class EgressDTO {
    private String name;
    private BigDecimal monthlyEgress;
}
