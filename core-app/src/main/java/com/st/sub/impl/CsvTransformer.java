package com.st.sub.impl;

import com.st.model.PriceFeed;
import com.st.repos.InMemoryStorage;
import com.st.sub.Transformer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static lombok.Lombok.checkNotNull;

/**
 * Data is collected from CSV and dispatch to local storage as pricefeed object.
 */
@Slf4j
public class CsvTransformer implements Transformer<String> {

    private static final String regex = ",";

    private InMemoryStorage inMemoryStorage;

    public CsvTransformer(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    @Override
    public boolean onTransform(@NonNull String csvData) {
        @NonNull
        String[] tmpData = csvData.split(regex);

        if (tmpData.length != 5){
            log.warn("Not able to transform data as size is incorrect", tmpData.length);
            return false;
        }
        log.info("received csvData:{}, with length:{}",csvData, tmpData.length);

        inMemoryStorage.store(PriceFeed.builder().
                                sequenceId(createSequenceId(checkNotNull(tmpData[0], "Expected a sequence number"))).
                                ccyPair(createCcyPair(checkNotNull(tmpData[1], "Incorrect ccyPair"))).
                                bid(createBid(checkNotNull (tmpData[2], "Incorrect Bid value"))).
                                ask(createAsk(checkNotNull (tmpData[3], "Incorrect Ask value"))).
                                timeStamp(createTimeStamp(checkNotNull (tmpData[4], "Incorrect time stamp value"))).
                                build());
        return true;
    }
}
