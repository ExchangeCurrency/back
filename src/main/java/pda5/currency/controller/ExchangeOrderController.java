package pda5.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pda5.currency.dto.exchange.ExchangeOrderRequestDTO;
import pda5.currency.dto.exchange.ExchangeOrderResponseDTO;
import pda5.currency.entity.ExchangeOrder;
import pda5.currency.service.ExchangeOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-orders")
public class ExchangeOrderController {

    @Autowired
    private ExchangeOrderService exchangeOrderService;

    /**
     * 환전 요청 생성
     */
    @PostMapping
    public ResponseEntity<ExchangeOrderResponseDTO> createExchangeOrder(@RequestBody ExchangeOrderRequestDTO requestDTO) {
        ExchangeOrderResponseDTO response = exchangeOrderService.createExchangeOrder(requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 사용자의 환전 요청 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExchangeOrderResponseDTO>> getExchangeOrdersByUserId(@PathVariable Integer userId) {
        List<ExchangeOrderResponseDTO> response = exchangeOrderService.getExchangeOrdersByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
