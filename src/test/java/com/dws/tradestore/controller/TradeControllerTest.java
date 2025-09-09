package com.dws.tradestore.controller;

import com.dws.tradestore.entity.Trade;
import com.dws.tradestore.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TradeControllerTest {

    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void testAddTrade_Success() throws Exception {
        Trade trade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), "N");
        when(tradeService.addTrade(any(Trade.class))).thenReturn(trade);

        String tradeJson = "{"
                + "\"tradeId\": \"T1\","
                + "\"version\": 1,"
                + "\"counterPartyId\": \"CP-1\","
                + "\"bookId\": \"B1\","
                + "\"maturityDate\": \"" + LocalDate.now().plusDays(1) + "\","
                + "\"createdDate\": \"" + LocalDate.now() + "\","
                + "\"expired\": \"N\""
                + "}";

        mockMvc.perform(post("/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tradeJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Trade added successfully."));
    }

    @Test
    void testAddTrade_Failure() throws Exception {
        doThrow(new IllegalArgumentException("Invalid trade")).when(tradeService).addTrade(any(Trade.class));

        String tradeJson = "{"
                + "\"tradeId\": \"T1\","
                + "\"version\": 1,"
                + "\"counterPartyId\": \"CP-1\","
                + "\"bookId\": \"B1\","
                + "\"maturityDate\": \"" + LocalDate.now().plusDays(1) + "\","
                + "\"createdDate\": \"" + LocalDate.now() + "\","
                + "\"expired\": \"N\""
                + "}";

        mockMvc.perform(post("/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tradeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid trade"));
    }

    @Test
    void testGetAllTrades() throws Exception {
        Trade trade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), "N");
        when(tradeService.getAllTrades()).thenReturn(List.of(trade));

        mockMvc.perform(get("/trades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tradeId").value("T1"));

        verify(tradeService, times(1)).updateExpiryFlags();
        verify(tradeService, times(1)).getAllTrades();
    }
}
