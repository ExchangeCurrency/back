package pda5.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5.currency.entity.ExchangeOrder;

import java.util.List;

public interface ExchangeOrderRepository extends JpaRepository<ExchangeOrder, Integer> {
    List<ExchangeOrder> findByUser_UserId(Integer userId);
}
