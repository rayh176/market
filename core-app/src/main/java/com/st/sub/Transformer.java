package com.st.sub;

import com.st.model.CcyPair;
import lombok.NonNull;

import java.time.LocalDateTime;

import static com.st.util.LocalDateUtil.convertStringToTimeStamp;

public interface Transformer<T> {

    boolean onTransform(@NonNull T data);

    default long createSequenceId(String seqId) {
        return Long.valueOf(seqId.trim());
    }

    default CcyPair createCcyPair(String ccyPair) {
        return CcyPair.builder().ccypair(ccyPair.trim()).build();
    }

    default Double createBid(String bid) {
        return Double.valueOf(bid.trim());
    }
    default Double createAsk(String ask) {
        return Double.valueOf(ask.trim());
    }

    default LocalDateTime createTimeStamp(String timeStamp) {
        return convertStringToTimeStamp(timeStamp);
    }
}
