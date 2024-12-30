package pda5.currency.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private Integer accountId;
    private Integer userId;
    private String currencyType;
    private Double balance;
}
