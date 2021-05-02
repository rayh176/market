package com.st.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Spread represent how much the raw price is effected by.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spread {
    private Double bidSpread = 0.1;
    private Double askSpread = 0.1;
}
