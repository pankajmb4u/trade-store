package com.dws.tradestore.controller;

import com.dws.tradestore.entity.Trade;
import com.dws.tradestore.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/trades")
public class TradeController {

    private TradeService tradeService;
    TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping
    public ResponseEntity<String> addTrade(@RequestBody Trade trade) {
        try {
            tradeService.addTrade(trade);
            return ResponseEntity.ok("Trade added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public Collection<Trade> getAllTrades() {
        tradeService.updateExpiryFlags();
        return tradeService.getAllTrades();
    }
}
