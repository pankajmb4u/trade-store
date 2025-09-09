package com.dws.tradestore.kafka;

import com.dws.tradestore.entity.Trade;
import com.dws.tradestore.service.TradeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TradeKafkaListener {

    private final TradeService tradeService;

    public TradeKafkaListener(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @KafkaListener(topics = "trade", groupId = "trade-store-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(Trade trade) {
        try {
        	tradeService.addTrade(trade);
            System.out.println("Trade received and processed: " + trade.getTradeId());
        } catch (Exception e) {
            System.err.println("Failed to process trade: " + e.getMessage());
        }
    }
}