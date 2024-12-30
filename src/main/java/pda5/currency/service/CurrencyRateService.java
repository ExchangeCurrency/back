package pda5.currency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pda5.currency.dto.currencyRate.CurrencyRateResponseDTO;
import pda5.currency.repository.CurrencyRateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;

    // 특정 currencyType의 환율 가져오기
    public List<CurrencyRateResponseDTO> getRatesByCurrencyType(String currencyType) {
        return currencyRateRepository
                .findByFromCurrency_CurrencyType(currencyType)
                .stream()
                .map(rate -> new CurrencyRateResponseDTO(
                        rate.getFromCurrency().getCurrencyType(),
                        rate.getFromCurrency().getName(),
                        rate.getFromCurrency().getSymbol(),
                        rate.getToCurrency().getCurrencyType(),
                        rate.getToCurrency().getName(),
                        rate.getToCurrency().getSymbol(),
                        rate.getRate() // 환율 값
                ))
                .collect(Collectors.toList());
    }

    // 모든 환율 가져오기
    public List<CurrencyRateResponseDTO> getAllRates() {
        return currencyRateRepository.findAll()
                .stream()
                .map(rate -> new CurrencyRateResponseDTO(
                        rate.getFromCurrency().getCurrencyType(),
                        rate.getFromCurrency().getName(),
                        rate.getFromCurrency().getSymbol(),
                        rate.getToCurrency().getCurrencyType(),
                        rate.getToCurrency().getName(),
                        rate.getToCurrency().getSymbol(),
                        rate.getRate() // 환율 값
                ))
                .collect(Collectors.toList());
    }
}
