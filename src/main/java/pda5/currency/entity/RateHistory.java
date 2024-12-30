package pda5.currency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pda5.currency.global.BasicEntity;
import java.time.LocalDateTime;


@Entity
@Table(name = "rate_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateHistory extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;

    @ManyToOne
    @JoinColumn(name = "currencyRates_id", nullable = false)
    private CurrencyRate currencyRate;

    private Double rate;

    private LocalDateTime recordedAt;
}
