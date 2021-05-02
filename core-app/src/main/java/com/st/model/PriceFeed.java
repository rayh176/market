package com.st.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Place holder for ticks
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceFeed {
    @NonNull
    private long sequenceId;
    @NonNull
    private CcyPair ccyPair;
    @NonNull
    private Double bid;
    @NonNull
    private Double ask;
    @NonNull
    private LocalDateTime timeStamp;
}
