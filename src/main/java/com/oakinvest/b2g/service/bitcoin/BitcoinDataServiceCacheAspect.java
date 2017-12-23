package com.oakinvest.b2g.service.bitcoin;

import com.oakinvest.b2g.dto.ext.bitcoin.bitcoind.BitcoindBlockData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Aspect adding a cache to BitcoinDataService.
 * Created by straumat on 30/06/17.
 */
@Configuration
@Aspect
public class BitcoinDataServiceCacheAspect {

    /**
     * Cache store.
     */
    private final BitcoinDataServiceCacheStore cacheStore;

    /**
     * Bitcoind cache loader.
     */
    private final BitcoinDataServiceCacheLoader bitcoindCacheLoader;

    /**
     * Constructor.
     * @param newCacheStore cache store
     * @param newBitcoindCacheLoader cache loader
     */
    public BitcoinDataServiceCacheAspect(final BitcoinDataServiceCacheStore newCacheStore, final BitcoinDataServiceCacheLoader newBitcoindCacheLoader) {
        this.cacheStore = newCacheStore;
        this.bitcoindCacheLoader = newBitcoindCacheLoader;
    }

    /**
     * Cache for getBlockCountFromCache().
     * @param pjp object
     * @return block count
     * @throws Throwable exception
     */
    @SuppressWarnings("unchecked")
    @Around("execution(* com.oakinvest.b2g.service.BitcoinDataService.getBlockCount())")
    public final Optional<Integer> getBlockCount(final ProceedingJoinPoint pjp) throws Throwable {
        // If the data is outdated.
        if (cacheStore.isBlockCountOutdated()) {
            Optional<Integer> blockCount = ((Optional<Integer>) pjp.proceed(new Object[]{}));
            if (blockCount.isPresent()) {
                cacheStore.updateBlockCountInCache(blockCount.get());
                return Optional.of(blockCount.get());
            } else {
                return Optional.empty();
            }
        // If the data is still in cache.
        } else {
            if (cacheStore.getBlockCountFromCache() != -1) {
                return Optional.of(cacheStore.getBlockCountFromCache());
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * Cache for getBlockDataFromCache().
     * @param pjp object
     * @param blockHeight block height
     * @return block data
     * @throws Throwable exception
     */
    @SuppressWarnings("unchecked")
    @Around("execution(* com.oakinvest.b2g.service.BitcoinDataService.getBlockData(..)) && args(blockHeight))")
    public final Optional<BitcoindBlockData> getBlockData(final ProceedingJoinPoint pjp, final int blockHeight) throws Throwable {
        // Loading cache.
        bitcoindCacheLoader.loadCache(blockHeight);

        // Returning the data.
        return (Optional<BitcoindBlockData>) pjp.proceed(new Object[]{ blockHeight });
    }

}
