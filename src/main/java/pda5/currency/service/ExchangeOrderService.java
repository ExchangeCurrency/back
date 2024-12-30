package pda5.currency.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pda5.currency.dto.exchange.ExchangeOrderRequestDTO;
import pda5.currency.dto.exchange.ExchangeOrderResponseDTO;
import pda5.currency.entity.*;
import pda5.currency.global.CustomException;
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
                .orElseThrow(() -> new CustomException("User not found", "USER_NOT_FOUND"));

        Currency fromCurrency = currencyRepository.findById(requestDTO.getFromCurrencyId())
                .orElseThrow(() -> new CustomException("From Currency not found", "FROM_CURRENCY_NOT_FOUND"));

        Currency toCurrency = currencyRepository.findById(requestDTO.getToCurrencyId())
                .orElseThrow(() -> new CustomException("To Currency not found", "TO_CURRENCY_NOT_FOUND"));

        CurrencyRate currencyRate = currencyRateRepository.findByFromCurrency_CurrencyIdAndToCurrency_CurrencyId(
                        requestDTO.getFromCurrencyId(), requestDTO.getToCurrencyId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new CustomException("Exchange rate not found", "EXCHANGE_RATE_NOT_FOUND"));

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

        // 계좌 업데이트
        Account fromAccount = accountRepository.findByUser_UserId(requestDTO.getUserId()).stream()
                .filter(account -> account.getCurrency().getCurrencyId().equals(requestDTO.getFromCurrencyId()))
                .findFirst()
                .orElseThrow(() -> new CustomException("Source account not found", "SOURCE_ACCOUNT_NOT_FOUND"));

        if (fromAccount.getBalance() < requestDTO.getFromAmount()) {
            throw new CustomException("Insufficient balance", "INSUFFICIENT_BALANCE");
        }

        fromAccount.setBalance(fromAccount.getBalance() - requestDTO.getFromAmount());
        accountRepository.save(fromAccount);

        Account toAccount = accountRepository.findByUser_UserId(requestDTO.getUserId()).stream()
                .filter(account -> account.getCurrency().getCurrencyId().equals(requestDTO.getToCurrencyId()))
                .findFirst()
                .orElseThrow(() -> new CustomException("Target account not found", "TARGET_ACCOUNT_NOT_FOUND"));

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
