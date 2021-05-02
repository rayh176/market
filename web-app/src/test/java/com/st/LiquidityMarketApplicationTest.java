package com.st;

import com.st.config.MarketConfig;
import com.st.pub.PriceProducer;
import com.st.repos.InMemoryStorage;
import com.st.service.LiquidityService;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes={MarketConfig.class, PriceProducer.class, InMemoryStorage.class})
@ComponentScan("com.st")
@EnableScheduling
public class LiquidityMarketApplicationTest {

    @SpyBean
    LiquidityService liquidityService;

    @Autowired
    PriceProducer priceProducer;

    @Test
    public void testFindAllPrices(){
        try {
            priceProducer.sendPrice();

            await().atMost(Duration.FIVE_MINUTES).until(() -> {
                liquidityService.findAllPrices().stream().forEach(price -> System.out.println("Tick details="+price));
                return true;
            });
        } catch(Exception ex){
            fail("Failed to get prices from the pricer", ex);
        }

    }


}
