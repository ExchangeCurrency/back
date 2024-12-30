package pda5.currency.service;

import lombok.RequiredArgsConstructor;
import pda5.currency.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pda5.currency.dto.exchange.ExchangeOrderRequestDTO;
import pda5.currency.dto.exchange.ExchangeOrderResponseDTO;
import pda5.currency.entity.Account;
import pda5.currency.entity.Currency;
import pda5.currency.entity.CurrencyRate;
import pda5.currency.entity.ExchangeOrder;
import pda5.currency.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeOrderService {

    private final ExchangeOrderRepository exchangeOrderRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public ExchangeOrderResponseDTO createExchangeOrder(ExchangeOrderRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Currency fromCurrency = currencyRepository.findById(requestDTO.getFromCurrencyId())
                .orElseThrow(() -> new RuntimeException("From Currency not found"));

        Currency toCurrency = currencyRepository.findById(requestDTO.getToCurrencyId())
                .orElseThrow(() -> new RuntimeException("To Currency not found"));

        CurrencyRate currencyRate = currencyRateRepository.findByFromCurrency_CurrencyIdAndToCurrency_CurrencyId(
                        requestDTO.getFromCurrencyId(), requestDTO.getToCurrencyId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));

        Double toAmount = requestDTO.getFromAmount() * currencyRate.getRate();

        // 환전 요청 생성
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setUser(user);
        exchangeOrder.setFromCurrency(fromCurrency);
        exchangeOrder.setToCurrency(toCurrency);
        exchangeOrder.setFromAmount(requestDTO.getFromAmount());
        exchangeOrder.setToAmount(toAmount);
        exchangeOrder.setRate(currencyRate.getRate());
        exchangeOrder.setStatus("Pending");
        exchangeOrder.setCreatedAt(LocalDateTime.now());

        ExchangeOrder savedOrder = exchangeOrderRepository.save(exchangeOrder);

        //계좌 업데이트
        Account fromAccount = accountRepository.findByUser_UserId(requestDTO.getUserId()).stream()
                .filter(account -> account.getCurrency().getCurrencyId().equals(requestDTO.getFromCurrencyId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        if (fromAccount.getBalance() < requestDTO.getFromAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - requestDTO.getFromAmount());
        accountRepository.save(fromAccount);

        Account toAccount = accountRepository.findByUser_UserId(requestDTO.getUserId()).stream()
                .filter(account -> account.getCurrency().getCurrencyId().equals(requestDTO.getToCurrencyId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        toAccount.setBalance(toAccount.getBalance() + toAmount);
        accountRepository.save(toAccount);

        savedOrder.setStatus("Completed");
        exchangeOrderRepository.save(savedOrder);

        return new ExchangeOrderResponseDTO(
                savedOrder.getOrderId(),
                savedOrder.getUser().getUserId(),
                savedOrder.getFromCurrency().getCurrencyType(),
                savedOrder.getToCurrency().getCurrencyType(),
                savedOrder.getFromAmount(),
                savedOrder.getToAmount(),
                savedOrder.getRate(),
                savedOrder.getStatus()
        );
    }


    public List<ExchangeOrderResponseDTO> getExchangeOrdersByUserId(Integer userId) {
        List<ExchangeOrder> exchangeOrders = exchangeOrderRepository.findByUser_UserId(userId);

        return exchangeOrders.stream().map(order -> new ExchangeOrderResponseDTO(
                order.getOrderId(),
                order.getUser().getUserId(),
                order.getFromCurrency().getCurrencyType(),
                order.getToCurrency().getCurrencyType(),
                order.getFromAmount(),
                order.getToAmount(),
                order.getRate(),
                order.getStatus()
        )).collect(Collectors.toList());
    }
}
