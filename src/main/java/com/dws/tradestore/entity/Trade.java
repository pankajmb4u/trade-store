package com.dws.tradestore.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Trade {
    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    private String expired;

    // No-argument constructor
    public Trade() {}

    // All-argument constructor
    public Trade(String tradeId, int version, String counterPartyId, String bookId,
                 LocalDate maturityDate, LocalDate createdDate, String expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    // Getters and setters
    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public String getCounterPartyId() { return counterPartyId; }
    public void setCounterPartyId(String counterPartyId) { this.counterPartyId = counterPartyId; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public LocalDate getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDate maturityDate) { this.maturityDate = maturityDate; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public String getExpired() { return expired; }
    public void setExpired(String expired) { this.expired = expired; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return version == trade.version &&
                Objects.equals(tradeId, trade.tradeId) &&
                Objects.equals(counterPartyId, trade.counterPartyId) &&
                Objects.equals(bookId, trade.bookId) &&
                Objects.equals(maturityDate, trade.maturityDate) &&
                Objects.equals(createdDate, trade.createdDate) &&
                Objects.equals(expired, trade.expired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, version, counterPartyId, bookId, maturityDate, createdDate, expired);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterPartyId='" + counterPartyId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", maturityDate=" + maturityDate +
                ", createdDate=" + createdDate +
                ", expired='" + expired + '\'' +
                '}';
    }
}