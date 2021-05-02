package com.st.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

/**
 * PlaceHolder for currencies pair names
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CcyPair {
    // Usually CcyPair is expressed as one unit EUR/USD instead of splitting it to base and counter
    @NonNull
    private String ccypair;
}
