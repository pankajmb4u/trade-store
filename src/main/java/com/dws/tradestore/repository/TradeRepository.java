package com.dws.tradestore.repository;

import com.dws.tradestore.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Optional<Trade> findByTradeIdAndVersion(String tradeId, int version);
    Optional<Trade> findTopByTradeIdOrderByVersionDesc(String tradeId);
}