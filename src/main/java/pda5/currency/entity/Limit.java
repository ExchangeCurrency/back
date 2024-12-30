package pda5.currency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pda5.currency.global.BasicEntity;

@Entity
@Table(name = "exchange_limit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limit extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer limitId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double dailyLimit;

    private Double monthlyLimit;

    private Double currentDailyUsed;

    private Double currentMonthUsed;
}
