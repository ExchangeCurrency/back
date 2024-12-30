package pda5.currency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda5.currency.dto.currencyRate.CurrencyRateResponseDTO;
import pda5.currency.service.CurrencyRateService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency-rates")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    // 특정 currencyType의 현재 환율 조회
    @GetMapping("/{currencyType}")
    public List<CurrencyRateResponseDTO> getRatesByCurrencyType(@PathVariable String currencyType) {
        return currencyRateService.getRatesByCurrencyType(currencyType);
    }

    // 전체 환율 조회
    @GetMapping
    public List<CurrencyRateResponseDTO> getAllRates() {
        return currencyRateService.getAllRates();
    }
}
