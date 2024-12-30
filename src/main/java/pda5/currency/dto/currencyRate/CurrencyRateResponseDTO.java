package pda5.currency.dto.currencyRate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateResponseDTO {
    private String fromCurrencyType;
    private String fromCurrencyName;
    private String fromCurrencySymbol;

    private String toCurrencyType;
    private String toCurrencyName;
    private String toCurrencySymbol;

    private Double rate; // 환율 값
}
