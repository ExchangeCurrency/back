package pda5.currency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pda5.currency.global.BasicEntity;

@Entity
@Table(name = "bank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    private Double balance;
}
