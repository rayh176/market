package com.st.sub.impl;

import com.st.sub.Listener;
import com.st.sub.Transformer;
import lombok.extern.slf4j.Slf4j;

/**
 * Listen to price feed messages that were produced
 */
@Slf4j
public class PriceFeedListener implements Listener<String> {

    private Transformer<String> transformer;

    public PriceFeedListener(Transformer<String> transformer) {
        this.transformer = transformer;
    }

    @Override
    public boolean onMessage(String message) {
        if ( message == null || message.length() == 0 || message.chars().allMatch(Character::isWhitespace) || !message.contains(",")){
           log.warn("Received corrupted data, message not processed");
           return false;
        }
        return transformer.onTransform(message);
    }
}
