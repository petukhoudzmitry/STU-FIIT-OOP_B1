package com.petition.platform.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class AbstractPetition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    protected UUID id;

    @Column(name = "title", nullable = false, length = 60)
    protected String title;

    @Column(name = "text", nullable = false, length = 500)
    protected String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", nullable = false)
    protected SimpleUser creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    protected CompanyUser company;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "valid_until", nullable = false)
    protected LocalDateTime validUntil;

    @Column(name = "is_valid", nullable = false)
    protected Boolean isValid;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SimpleUser getCreator() {
        return creator;
    }

    public void setCreator(SimpleUser creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime valid_until) {
        this.validUntil = valid_until;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean is_valid) {
        this.isValid = is_valid;
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        validUntil = createdAt.plusDays(30L);
    }

    public CompanyUser getCompany() {
        return company;
    }

    public void setCompany(CompanyUser company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return id + " " + title + " " + text + " " + creator + " " + company + " " + createdAt + " " + validUntil;
    }
}
