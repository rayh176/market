package com.st.service;

import com.st.calc.PriceFeedModifier;
import com.st.model.CcyPair;
import com.st.model.PriceFeed;
import com.st.repos.InMemoryStorage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service retrieve the data from the storage and apply the spread before sending out to the rest
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LiquidityService {

    @NonNull
    InMemoryStorage inMemoryStorage;

    @NonNull
    PriceFeedModifier priceFeedModifier;

    public List<PriceFeed> findAllPrices() {
        Collection<PriceFeed> prices = inMemoryStorage.findAll();
        return prices.stream().
                map(priceFeed -> priceFeedModifier.priceFeedWithSpread(priceFeed)).
                collect(Collectors.toList());
    }

    public PriceFeed findPricePerCcyPair(String ccyPair){
        PriceFeed priceFeed = inMemoryStorage.findPerCcyPair(CcyPair.builder().ccypair(ccyPair).build());
        return priceFeedModifier.priceFeedWithSpread(priceFeed);
    }
}
