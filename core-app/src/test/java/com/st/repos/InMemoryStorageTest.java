package com.st.repos;

import com.st.model.CcyPair;
import com.st.model.PriceFeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.st.util.LocalDateUtil.convertStringToTimeStamp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InMemoryStorageTest {

    private InMemoryStorage inMemoryStorage;

    @BeforeEach
    public void setUp(){
        inMemoryStorage = new InMemoryStorage();
    }

    @ParameterizedTest
    @MethodSource("dataToStore")
    public void testFindPerCcyPairHoldCorrectData(PriceFeed priceFeed){
        inMemoryStorage.store(priceFeed);
        assertThat(inMemoryStorage.size(), is(1));
    }

    @ParameterizedTest
    @MethodSource("storeAll")
    public void testFindAll(List<PriceFeed> priceFeed){
        priceFeed.stream().forEach(p -> inMemoryStorage.store(p));
        assertThat(inMemoryStorage.size(), is(5));
        Collection<PriceFeed> priceFeedCollection = inMemoryStorage.findAll();
        long matcher = priceFeedCollection.stream().filter(p -> "GBP/USD".equalsIgnoreCase(p.getCcyPair().getCcypair())
                                                || "EUR/USD".equalsIgnoreCase(p.getCcyPair().getCcypair())
                                                || "CHF/USD".equalsIgnoreCase(p.getCcyPair().getCcypair())
                                                || "AUD/USD".equalsIgnoreCase(p.getCcyPair().getCcypair())
                                                || "CAD/USD".equalsIgnoreCase(p.getCcyPair().getCcypair())
        ).count();
        assertThat(matcher, is(5L));
    }

    public static Stream<Arguments> dataToStore(){
        return Stream.of(Arguments.of(createPriceFeed(1L, CcyPair.builder().ccypair("GBP/USD").build(), 1.2, 1.25, convertStringToTimeStamp("02-05-2021 16:22:27:788"))),
                Arguments.of(createPriceFeed(1L, CcyPair.builder().ccypair("EUR/USD").build(), 1.4, 1.45, convertStringToTimeStamp("02-05-2021 16:22:27:788"))),
                Arguments.of(createPriceFeed(1L, CcyPair.builder().ccypair("CHF/USD").build(), 1.5, 1.55, convertStringToTimeStamp("02-05-2021 16:22:27:788"))),
                Arguments.of(createPriceFeed(1L, CcyPair.builder().ccypair("AUD/USD").build(), 1.6, 1.65, convertStringToTimeStamp("02-05-2021 16:22:27:788"))),
                Arguments.of(createPriceFeed(1L, CcyPair.builder().ccypair("CAD/USD").build(), 1.3, 1.35, convertStringToTimeStamp("02-05-2021 16:22:27:788")))
                );
    }

    public static Stream<Arguments> storeAll(){
        List<PriceFeed> priceFeeds = new ArrayList<>();
        priceFeeds.add(createPriceFeed(1L, CcyPair.builder().ccypair("GBP/USD").build(), 1.2, 1.25, convertStringToTimeStamp("02-05-2021 16:22:27:788")));
        priceFeeds.add(createPriceFeed(1L, CcyPair.builder().ccypair("EUR/USD").build(), 1.4, 1.45, convertStringToTimeStamp("02-05-2021 16:22:27:788")));
        priceFeeds.add(createPriceFeed(1L, CcyPair.builder().ccypair("CHF/USD").build(), 1.5, 1.55, convertStringToTimeStamp("02-05-2021 16:22:27:788")));
        priceFeeds.add(createPriceFeed(1L, CcyPair.builder().ccypair("AUD/USD").build(), 1.6, 1.65, convertStringToTimeStamp("02-05-2021 16:22:27:788")));
        priceFeeds.add(createPriceFeed(1L, CcyPair.builder().ccypair("CAD/USD").build(), 1.3, 1.35, convertStringToTimeStamp("02-05-2021 16:22:27:788")));

        return Stream.of(Arguments.of(priceFeeds)
        );
    }

    private static PriceFeed createPriceFeed(Long id, CcyPair ccyPair, double bid, double ask, LocalDateTime timeStamp){
        return PriceFeed.builder().
                sequenceId(id).
                ccyPair(ccyPair).
                bid(bid).
                ask(ask).
                timeStamp(timeStamp)
        .build();
    }

}
