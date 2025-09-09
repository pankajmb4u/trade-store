package com.dws.tradestore.controller;

import com.dws.tradestore.entity.Trade;
import com.dws.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping
    public String addTrade(@RequestBody Trade trade) {
        try {
            tradeService.addTrade(trade);
            return "Trade added successfully.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping
    public Collection<Trade> getAllTrades() {
        tradeService.updateExpiryFlags();
        return tradeService.getAllTrades();
    }
}
