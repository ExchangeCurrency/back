package pda5.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pda5.currency.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUser_UserId(Integer userId);
}
