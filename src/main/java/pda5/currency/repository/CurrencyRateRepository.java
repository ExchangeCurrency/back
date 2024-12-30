package pda5.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5.currency.entity.CurrencyRate;

import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Integer> {
    // fromCurrency의 currencyType으로 환율 검색
    List<CurrencyRate> findByFromCurrency_CurrencyType(String fromCurrencyType);
    // 특정 currencyType에 해당하는 환율 검색
    List<CurrencyRate> findByFromCurrency_CurrencyTypeOrToCurrency_CurrencyType(String fromCurrencyType, String toCurrencyType);
    List<CurrencyRate> findByFromCurrency_CurrencyIdAndToCurrency_CurrencyId(Integer fromCurrencyId, Integer toCurrencyId);
}
