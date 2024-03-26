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


    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    protected SimpleUser creator;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime created_at;

    @Column(name = "valid_until", nullable = false)
    protected LocalDateTime valid_until;

    @Column(name = "is_valid", nullable = false)
    protected Boolean is_valid;

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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getValid_until() {
        return valid_until;
    }

    public void setValid_until(LocalDateTime valid_until) {
        this.valid_until = valid_until;
    }

    public Boolean getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(Boolean is_valid) {
        this.is_valid = is_valid;
    }

    @PrePersist
    protected void onCreate(){
        created_at = LocalDateTime.now();
        valid_until = created_at.plusDays(30L);
    }
}
