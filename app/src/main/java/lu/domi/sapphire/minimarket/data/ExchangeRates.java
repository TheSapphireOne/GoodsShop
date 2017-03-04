package lu.domi.sapphire.minimarket.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Notice: Class, rates are based on USD
 */
public class ExchangeRates {
    // Timestamp form when it was last time updated
    private final static long dayMillis = 86400000;
    private long timestamp;
    private Map<String, BigDecimal> currencies = new HashMap<>();

    public ExchangeRates() {}

    public ExchangeRates(long timestamp, String currencyIso, BigDecimal rate) {
        addRate(currencyIso, rate);
    }

    public void addRate(String currencyIso, BigDecimal rate) {
        currencies.put(currencyIso, rate);
    }

    public BigDecimal getRate(String currencyIso) {
        return currencies.get(currencyIso);
    }

    public Set<String> getCurrencies() {
        return currencies.keySet();
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isUpToDate() {
        return (System.currentTimeMillis() - timestamp) < dayMillis;
    }
}
