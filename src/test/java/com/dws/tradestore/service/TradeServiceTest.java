package com.dws.tradestore.service;

import com.dws.tradestore.entity.Trade;
import com.dws.tradestore.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TradeServiceTest {

    private TradeRepository tradeRepository;
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        tradeRepository = Mockito.mock(TradeRepository.class);
        tradeService = new TradeService(tradeRepository); // Use constructor injection
    }

    @Test
    void testAddTrade_Success() {
        Trade trade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), "N");
        when(tradeRepository.findTopByTradeIdOrderByVersionDesc(anyString())).thenReturn(Optional.empty());
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        assertDoesNotThrow(() -> tradeService.addTrade(trade));
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testAddTrade_RejectLowerVersion() {
        Trade trade1 = new Trade("T1", 2, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), "N");
        Trade trade2 = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), "N");

        when(tradeRepository.findTopByTradeIdOrderByVersionDesc(eq("T1"))).thenReturn(Optional.of(trade1));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> tradeService.addTrade(trade2));
        assertEquals("Lower version trade received. Rejecting trade.", ex.getMessage());
    }

    @Test
    void testAddTrade_RejectPastMaturity() {
        Trade trade = new Trade("T2", 1, "CP-2", "B1", LocalDate.now().minusDays(1), LocalDate.now(), "N");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> tradeService.addTrade(trade));
        assertEquals("Trade maturity date is before today.", ex.getMessage());
    }

    @Test
    void testUpdateExpiryFlags() {
        Trade trade1 = new Trade("T3", 1, "CP-3", "B2", LocalDate.now().minusDays(2), LocalDate.now(), "N");
        Trade trade2 = new Trade("T4", 1, "CP-4", "B3", LocalDate.now().plusDays(2), LocalDate.now(), "N");
        List<Trade> trades = List.of(trade1, trade2);

        when(tradeRepository.findAll()).thenReturn(trades);
        when(tradeRepository.saveAll(anyList())).thenReturn(trades);

        tradeService.updateExpiryFlags();

        assertEquals("Y", trade1.getExpired());
        assertEquals("N", trade2.getExpired());
        verify(tradeRepository, times(1)).saveAll(anyList());
    }
}