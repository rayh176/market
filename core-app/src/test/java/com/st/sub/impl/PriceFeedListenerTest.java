package com.st.sub.impl;

import com.st.sub.Listener;
import com.st.sub.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceFeedListenerTest {

    private Listener<String> priceFeedListener;
    private Transformer<String> transformer;

    @BeforeEach
    public void before(){
        transformer = mock(Transformer.class);
        priceFeedListener = new PriceFeedListener(transformer);
    }

    @ParameterizedTest
    @MethodSource("corruptMessage")
    public void testEmptyData(String message){
        // Test should generate no errors
        boolean status = priceFeedListener.onMessage(message);
        assertThat(status, is(false));
    }

    @Test
    public void testCorrectMessage(){
        String message = "95, EUR/USD, 1.1000, 1.2000, 01-06-2020 11:59:01:001";
        when(transformer.onTransform(message)).thenReturn(true);
        boolean status = priceFeedListener.onMessage(message);
        assertThat(status, is(true));
    }

    public static Stream<Arguments> corruptMessage(){
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" ")
                );
    }
}
