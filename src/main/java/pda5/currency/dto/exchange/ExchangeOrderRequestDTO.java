package pda5.currency.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOrderRequestDTO {
    private Integer userId;
    private Integer fromCurrencyId;
    private Integer toCurrencyId;
    private Double fromAmount;
}
