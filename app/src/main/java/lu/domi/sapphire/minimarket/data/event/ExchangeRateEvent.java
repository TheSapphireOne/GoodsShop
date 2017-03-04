package lu.domi.sapphire.minimarket.data.event;

import lu.domi.sapphire.minimarket.data.ExchangeRates;

/**
 * POJO, for currency exchange rates
 */
public class ExchangeRateEvent {

    private final boolean successful;
    private ExchangeRates exchangeRates;


    public ExchangeRateEvent(ExchangeRates exchangeRates, boolean successful) {
        this.exchangeRates = exchangeRates;
        this.successful = successful;
    }

    public ExchangeRateEvent(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }

}
