package com.st.calc;

import com.st.model.CcyPair;
import com.st.model.PriceFeed;
import com.st.model.Spread;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.st.util.LocalDateUtil.convertStringToTimeStamp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceFeedModifierTest {

    @ParameterizedTest
    @MethodSource("priceFeedData")
    public void testPriceFeedWithSpreadValueZero(long seq, CcyPair ccyPair, double bid, double ask, LocalDateTime timeStamp) {
        Spread spread = Spread.builder().bidSpread(0.0).askSpread(0.0).build();
        PriceFeedModifier modifier = new PriceFeedModifier(spread);
        PriceFeed priceFeed = PriceFeed.builder().sequenceId(seq).ccyPair(ccyPair).bid(bid).ask(ask).timeStamp(timeStamp).build();
        PriceFeed calculatedPriceFeed = modifier.priceFeedWithSpread(priceFeed);
        assertThat(bid, is(calculatedPriceFeed.getBid()));
        assertThat(ask, is(calculatedPriceFeed.getAsk()));
    }

    @ParameterizedTest
    @MethodSource("singlePriceFeedData")
    public void testPriceFeedWithSpreadValueNonZero(long seq, CcyPair ccyPair, double bid, double ask, LocalDateTime timeStamp) {
        Spread spread = Spread.builder().bidSpread(1.2).askSpread(1.1).build();
        PriceFeedModifier modifier = new PriceFeedModifier(spread);
        PriceFeed priceFeed = PriceFeed.builder().sequenceId(seq).ccyPair(ccyPair).bid(bid).ask(ask).timeStamp(timeStamp).build();
        PriceFeed calculatedPriceFeed = modifier.priceFeedWithSpread(priceFeed);
        assertThat(1.383, is(calculatedPriceFeed.getBid()));
        assertThat(1.466, is(calculatedPriceFeed.getAsk()));
    }

    private static Stream<Arguments> priceFeedData(){
        return Stream.of(Arguments.of(1L, CcyPair.builder().ccypair("EUR/USD").build(), 1.4, 1.45, convertStringToTimeStamp("02-05-2021 16:22:27:788")),
                Arguments.of(1L, CcyPair.builder().ccypair("GBP/USD").build(), 1.2, 1.25, convertStringToTimeStamp("02-05-2021 16:22:27:788")),
                Arguments.of(1L, CcyPair.builder().ccypair("CHF/USD").build(), 1.2, 1.25, convertStringToTimeStamp("02-05-2021 16:22:27:788"))
        );
    }

    private static Stream<Arguments> singlePriceFeedData(){
        return Stream.of(Arguments.of(1L, CcyPair.builder().ccypair("EUR/USD").build(), 1.4, 1.45, convertStringToTimeStamp("02-05-2021 16:22:27:788"))
        );
    }

}
