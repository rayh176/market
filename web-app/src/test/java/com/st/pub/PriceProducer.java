package com.st.pub;

import com.st.sub.Listener;
import com.st.util.LocalDateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

import static com.st.util.LocalDateUtil.DATE_TIME_FORMATTER;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceProducer {
    private final String [] ccyPairs = new String [] {"EUR/USD", "EUR/GBP", "EUR/CHF", "EUR/CAD" , "EUR/JPY", "GBP/USD"};

    @NonNull
    private Listener<String> listener;

    @Scheduled(fixedRate = 1, initialDelay = 1000)
    public void sendPrice() {
        Random r=new Random();
        int ticks = ThreadLocalRandom.current().nextInt(1, 30);
        for (int i = 1; i <= ticks; i++) {
            double mid = ThreadLocalRandom.current().nextDouble(1, 50);
            double spread = ThreadLocalRandom.current().nextDouble(0.0035, 1);
            int rPair=r.nextInt(ccyPairs.length);
            StringJoiner joiner = new StringJoiner(",");
            joiner.add(String.valueOf(i)).add(ccyPairs[rPair]).add(toBeTruncated((mid - (spread / 2)))).add(toBeTruncated(mid + (spread / 2))).add(LocalDateTime.now().format(DATE_TIME_FORMATTER));
            System.out.println("Price tick="+joiner.toString());
            listener.onMessage(joiner.toString());
        }
    }

    private String toBeTruncated(Double value){
        return BigDecimal.valueOf(value)
                .setScale(3, RoundingMode.HALF_UP)
                .toString();
    }
}
