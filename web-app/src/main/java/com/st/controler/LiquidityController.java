package com.st.controler;

import com.st.model.PriceFeed;
import com.st.service.LiquidityService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller provides two services, one to find all the latest prices for currencies and another end point per currency pair
 */
@Slf4j
@RestController
@RequestMapping("/v1/liquidity")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LiquidityController {

	@NonNull
	private LiquidityService liquidityService;


	@GetMapping("//{ccypair}")
	public PriceFeed findPriceByCcyPair(@PathVariable String ccyPair) {
		log.info("Request price for a currency pair", ccyPair);
		return liquidityService.findPricePerCcyPair(ccyPair);
	}

	@GetMapping
	public List<PriceFeed> findAll() {
		log.info("Request price for all currencies pair");
		return liquidityService.findAllPrices();
	}
}
