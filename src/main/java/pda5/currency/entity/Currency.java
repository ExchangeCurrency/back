package pda5.currency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pda5.currency.global.BasicEntity;

@Entity
@Table(name = "currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer currencyId;

    private String currencyType;

    private String name;

    private String symbol;
}
