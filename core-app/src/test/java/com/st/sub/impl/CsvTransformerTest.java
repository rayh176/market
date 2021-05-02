package com.st.sub.impl;

import com.st.repos.InMemoryStorage;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvTransformerTest {

    private Transformer<String> transformer;
    private InMemoryStorage inMemoryStorage;

    @BeforeEach
    public void setUp(){
        inMemoryStorage = mock(InMemoryStorage.class);
        transformer = new CsvTransformer(inMemoryStorage);
    }

    @ParameterizedTest
    @MethodSource("corruptMessage")
    public void testNonTransformableMessage(String message){
        boolean status = transformer.onTransform(message);
        assertThat(status, is(false));
    }

    @Test
    public void testTransformableMessage(){
        boolean status = transformer.onTransform("95, EUR/USD, 1.1000, 1.2000, 01-06-2020 11:59:01:001");
        assertThat(status, is(true));
    }

    public static Stream<Arguments> corruptMessage() {
        return Stream.of(Arguments.of("96, EUR/USD, 1.1000, 1.2000-01-06-2020 11:59:01:001"),
                Arguments.of("96, EUR/USD, 1.1000, 1.2000+01-06-2020 11:59:01:001"));
    }
}
