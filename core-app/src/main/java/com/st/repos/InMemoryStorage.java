package com.st.repos;

import com.st.model.CcyPair;
import com.st.model.PriceFeed;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data store for our liquidities
 * Prices are stored as spread only applied when objects are taken out of the storage
 */
@Repository
public class InMemoryStorage {

    private final Map<CcyPair, PriceFeed> storage = new ConcurrentHashMap<>();

    /**
     * Store the data in memory place holder
     * @param priceFeed
     */
    public void store(PriceFeed priceFeed) {
        storage.put(priceFeed.getCcyPair(), priceFeed);
    }

    public PriceFeed findPerCcyPair(CcyPair ccyPair){
        return storage.get(ccyPair);
    }

    /**
     * Return the list of elements
     * @return
     */
    public Collection<PriceFeed> findAll(){
        return storage.values();
    }

    public int size(){
        return storage.size();
    }
}
