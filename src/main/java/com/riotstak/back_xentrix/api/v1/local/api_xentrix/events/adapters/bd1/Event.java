
package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1;

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

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1.Category;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1.Venue;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "capacity_total")
    private Integer capacityTotal;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "cancel_reason", columnDefinition = "TEXT")
    private String cancelReason;

    @Column(name = "featured")
    private Boolean featured;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(name = "organizer_id")
    private Long organizerId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Event() {
    }

    public Event(String cancelReason, Integer capacityTotal, Category category, LocalDateTime createdAt,
            LocalDateTime deletedAt, String description, LocalDateTime endAt, Boolean featured, Long id,
            String imageUrl, Long organizerId, LocalDateTime startAt, String status, String title,
            LocalDateTime updatedAt, Venue venue) {
        this.cancelReason = cancelReason;
        this.capacityTotal = capacityTotal;
        this.category = category;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.description = description;
        this.endAt = endAt;
        this.featured = featured;
        this.id = id;
        this.imageUrl = imageUrl;
        this.organizerId = organizerId;
        this.startAt = startAt;
        this.status = status;
        this.title = title;
        this.updatedAt = updatedAt;
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCapacityTotal() {
        return capacityTotal;
    }

    public void setCapacityTotal(Integer capacityTotal) {
        this.capacityTotal = capacityTotal;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
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
        return "Event [id=" + id + ", title=" + title + ", description=" + description + ", startAt=" + startAt
                + ", endAt=" + endAt + ", status=" + status + ", capacityTotal=" + capacityTotal + ", imageUrl="
                + imageUrl + ", cancelReason=" + cancelReason + ", featured=" + featured + ", category=" + category
                + ", venue=" + venue + ", organizerId=" + organizerId + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + ", deletedAt=" + deletedAt + "]";
    }
}
