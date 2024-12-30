package pda5.currency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pda5.currency.global.BasicEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "FeeRates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeRate extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feeRateId;

    @Enumerated(EnumType.STRING)
    private User.Grade grade;

    private String currencyPair;

    private Double baseFee;

    private Double discountRate;

    private LocalDateTime effectiveStart;

    private LocalDateTime effectiveEnd;
}
