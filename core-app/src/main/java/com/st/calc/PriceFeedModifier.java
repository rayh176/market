package com.st.calc;

import com.st.model.PriceFeed;
import com.st.model.Spread;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

/**
 * Applying spread to the bid and ask
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceFeedModifier {

    @NonNull
    private Spread spread;

    public PriceFeed priceFeedWithSpread(PriceFeed original){
        return modifiedPrice.apply(original);
    }

    private Function<PriceFeed, PriceFeed> modifiedPrice = priceFeed -> {
        double bid = priceFeed.getBid();
        bid = (bid - (bid * spread.getBidSpread() / 100));

        double ask = priceFeed.getAsk();
        ask = (ask + (ask * spread.getAskSpread() / 100));

        return PriceFeed.builder().sequenceId(priceFeed.getSequenceId()).
                ccyPair(priceFeed.getCcyPair()).bid(toBeTruncated(bid)).ask(toBeTruncated(ask)).timeStamp(priceFeed.getTimeStamp()).build();
    };

    private double toBeTruncated(Double value){
        return BigDecimal.valueOf(value)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
