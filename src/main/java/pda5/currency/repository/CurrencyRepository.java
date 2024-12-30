package pda5.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5.currency.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {}
