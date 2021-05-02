package com.st.pub;

import com.st.sub.Listener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * Usually this class is an independent process but for the simplicity of our program
 * this class will be part of the spring boot process
 * Scheduled to read one line at the time
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceTick implements ApplicationRunner {

    public static final String PRICE_TICK_CSV = "priceTick.csv";

    @NonNull
    private Listener<String> listener;

    @Override
    public void run(ApplicationArguments args) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(PRICE_TICK_CSV);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + PRICE_TICK_CSV);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Stream<String> lines = reader.lines();
        log.info("Reading price ticks from the file", lines);
        lines.forEach(tick -> listener.onMessage(tick));
    }
}
