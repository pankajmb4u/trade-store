package com.dws.tradestore.entity;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeTest {

    @Test
    void testTradeConstructorAndGetters() {
        LocalDate maturityDate = LocalDate.now().plusDays(5);
        LocalDate createdDate = LocalDate.now();
        Trade trade = new Trade("T1", 1, "CP-1", "B1", maturityDate, createdDate, "N");

        assertEquals("T1", trade.getTradeId());
        assertEquals(1, trade.getVersion());
        assertEquals("CP-1", trade.getCounterPartyId());
        assertEquals("B1", trade.getBookId());
        assertEquals(maturityDate, trade.getMaturityDate());
        assertEquals(createdDate, trade.getCreatedDate());
        assertEquals("N", trade.getExpired());
    }

    @Test
    void testSetters() {
        Trade trade = new Trade();
        trade.setTradeId("T2");
        trade.setVersion(2);
        trade.setCounterPartyId("CP-2");
        trade.setBookId("B2");
        LocalDate maturityDate = LocalDate.now().plusDays(10);
        LocalDate createdDate = LocalDate.now();
        trade.setMaturityDate(maturityDate);
        trade.setCreatedDate(createdDate);
        trade.setExpired("Y");

        assertEquals("T2", trade.getTradeId());
        assertEquals(2, trade.getVersion());
        assertEquals("CP-2", trade.getCounterPartyId());
        assertEquals("B2", trade.getBookId());
        assertEquals(maturityDate, trade.getMaturityDate());
        assertEquals(createdDate, trade.getCreatedDate());
        assertEquals("Y", trade.getExpired());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate maturityDate = LocalDate.now().plusDays(1);
        LocalDate createdDate = LocalDate.now();
        Trade trade1 = new Trade("T1", 1, "CP-1", "B1", maturityDate, createdDate, "N");
        Trade trade2 = new Trade("T1", 1, "CP-1", "B1", maturityDate, createdDate, "N");

        assertEquals(trade1, trade2);
        assertEquals(trade1.hashCode(), trade2.hashCode());
    }

    @Test
    void testToString() {
        Trade trade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), "N");
        String str = trade.toString();
        assertTrue(str.contains("T1"));
        assertTrue(str.contains("CP-1"));
    }

    @Test
    void testMockTrade() {
        Trade mockTrade = Mockito.mock(Trade.class);
        when(mockTrade.getTradeId()).thenReturn("T100");
        when(mockTrade.getVersion()).thenReturn(5);

        assertEquals("T100", mockTrade.getTradeId());
        assertEquals(5, mockTrade.getVersion());
        verify(mockTrade, times(1)).getTradeId();
        verify(mockTrade, times(1)).getVersion();
    }
}
