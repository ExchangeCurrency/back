package pda5.currency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pda5.currency.global.BasicEntity;

@Entity
@Table(name = "exchange_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOrder extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double fromAmount;

    private Double toAmount;

    private Double rate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "from_currency",nullable = false)
    private Currency fromCurrency;

    @ManyToOne
    @JoinColumn(name = "to_currency",nullable = false)
    private Currency toCurrency;
}
