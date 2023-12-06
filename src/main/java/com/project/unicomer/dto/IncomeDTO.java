package com.project.unicomer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class IncomeDTO {
    private String name;
    private BigDecimal monthlyIncome;
}
