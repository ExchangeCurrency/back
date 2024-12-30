package pda5.currency.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOrderResponseDTO {
    private Integer orderId;
    private Integer userId;
    private String fromCurrency;
    private String toCurrency;
    private Double fromAmount;
    private Double toAmount;
    private Double rate;
    private String status;
}
