package com.st.config;

import com.st.calc.PriceFeedModifier;
import com.st.model.Spread;
import com.st.repos.InMemoryStorage;
import com.st.sub.Listener;
import com.st.sub.Transformer;
import com.st.sub.impl.CsvTransformer;
import com.st.sub.impl.PriceFeedListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketConfig {

    @Bean
    Transformer<String> createTransformer(InMemoryStorage inMemoryStorage){
        return new CsvTransformer(inMemoryStorage);
    }

    @Bean
    Listener<String> createListener(Transformer<String> transformer){
        return new PriceFeedListener(transformer);
    }

    @Bean
    Spread createSpread(){
        return new Spread();
    }

    @Bean
    PriceFeedModifier createPriceFeed(Spread spread){
        return new PriceFeedModifier(spread);
    }
}
