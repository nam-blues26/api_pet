package com.pet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private String date;

    @JsonProperty("count_bought")
    private BigDecimal countBought;
    @JsonProperty("count_cancel")
    private BigDecimal countCancel;
}
