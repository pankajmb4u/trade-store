package com.dws.tradestore.service;

import com.dws.tradestore.entity.Trade;
import com.dws.tradestore.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Trade addTrade(Trade trade) {
        // 1. Reject if maturity date is before today
        if (trade.getMaturityDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Trade maturity date is before today.");
        }

        // 2. Check for existing trade with same tradeId
        Optional<Trade> latestTradeOpt = tradeRepository.findTopByTradeIdOrderByVersionDesc(trade.getTradeId());
        if (latestTradeOpt.isPresent()) {
            Trade latestTrade = latestTradeOpt.get();
            if (trade.getVersion() < latestTrade.getVersion()) {
                throw new IllegalArgumentException("Lower version trade received. Rejecting trade.");
            }
        }

        // 3. Mark expired if maturity date is before today (should not happen due to check above)
        if (trade.getMaturityDate().isBefore(LocalDate.now())) {
            trade.setExpired("Y");
        } else {
            trade.setExpired("N");
        }

        return tradeRepository.save(trade);
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeByIdAndVersion(String tradeId, int version) {
        return tradeRepository.findByTradeIdAndVersion(tradeId, version);
    }

    // Optionally, add a method to update expired flags for all trades
    public void updateExpiryFlags() {
        List<Trade> trades = tradeRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Trade trade : trades) {
            if (trade.getMaturityDate().isBefore(today)) {
                trade.setExpired("Y");
            } else {
                trade.setExpired("N");
            }
        }
        tradeRepository.saveAll(trades);
    }
}
