
package com.riotstak.back_xentrix.api.v1.local.api_xentrix.ticket_types.adapters.bd1;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.Event;

@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "quantity_total")
    private Integer quantityTotal;

    @Column(name = "quantity_available")
    private Integer quantityAvailable;

    @Column(name = "sale_start_at")
    private LocalDateTime saleStartAt;

    @Column(name = "sale_end_at")
    private LocalDateTime saleEndAt;

    @Column(name = "version")
    private Integer version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public TicketType(LocalDateTime createdAt, String currency, LocalDateTime deletedAt, String description,
            Event event, Long id, String name, BigDecimal price, Integer quantityAvailable, Integer quantityTotal,
            LocalDateTime saleEndAt, LocalDateTime saleStartAt, LocalDateTime updatedAt, Integer version) {
        this.createdAt = createdAt;
        this.currency = currency;
        this.deletedAt = deletedAt;
        this.description = description;
        this.event = event;
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.quantityTotal = quantityTotal;
        this.saleEndAt = saleEndAt;
        this.saleStartAt = saleStartAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public TicketType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(Integer quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public LocalDateTime getSaleStartAt() {
        return saleStartAt;
    }

    public void setSaleStartAt(LocalDateTime saleStartAt) {
        this.saleStartAt = saleStartAt;
    }

    public LocalDateTime getSaleEndAt() {
        return saleEndAt;
    }

    public void setSaleEndAt(LocalDateTime saleEndAt) {
        this.saleEndAt = saleEndAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "TicketType [id=" + id + ", event=" + event + ", name=" + name + ", description=" + description
                + ", price=" + price + ", currency=" + currency + ", quantityTotal=" + quantityTotal
                + ", quantityAvailable=" + quantityAvailable + ", saleStartAt=" + saleStartAt + ", saleEndAt="
                + saleEndAt + ", version=" + version + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
                + ", deletedAt=" + deletedAt + "]";
    }
}
