package pda5.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5.currency.entity.CurrencyRate;

import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Integer> {
    List<CurrencyRate> findByFromCurrency_CurrencyIdAndToCurrency_CurrencyId(Integer fromCurrencyId, Integer toCurrencyId);
}
