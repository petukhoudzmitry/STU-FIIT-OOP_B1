package com.petition.platform.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Abstract class representing a generic petition.
 */
@MappedSuperclass
public class AbstractPetition implements Comparable<AbstractPetition>{

    /**
     * Default constructor for the petition.
     */
    public AbstractPetition() {}

    /**
     * Unique identifier for the petition.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    protected UUID id;

    /**
     * Title of the petition.
     */
    @Column(name = "title", nullable = false, length = 60)
    protected String title;

    /**
     * Text content of the petition.
     */
    @Column(name = "text", nullable = false, length = 500)
    protected String text;

    /**
     * Creator of the petition.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", nullable = false)
    protected SimpleUser creator;

    /**
     * Company associated with the petition.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    protected CompanyUser company;

    /**
     * List of users who have voted on the petition.
     */
    @ManyToMany(mappedBy = "signedPetitions", fetch = FetchType.EAGER)
    protected List<SimpleUser> voters;

    /**
     * Date and time when the petition was created.
     */
    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    /**
     * Date and time until which the petition is considered valid.
     */
    @Column(name = "valid_until", nullable = false)
    protected LocalDateTime validUntil;

    /**
     * Flag indicating whether the petition is valid.
     */
    @Column(name = "is_valid", nullable = false)
    protected Boolean isValid;

    /**
     * Retrieves the ID of the petition.
     *
     * @return ID of the petition.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the ID of the petition.
     *
     * @param id ID of the petition.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the petition.
     *
     * @return Title of the petition.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the petition.
     *
     * @param title Title of the petition.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the text of the petition.
     *
     * @return Text of the petition.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the petition.
     *
     * @param text Text of the petition.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Retrieves the creator of the petition.
     *
     * @return Creator of the petition.
     */
    public SimpleUser getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the petition.
     *
     * @param creator Creator of the petition.
     */
    public void setCreator(SimpleUser creator) {
        this.creator = creator;
    }

    /**
     * Retrieves the creation date of the petition.
     *
     * @return Creation date of the petition.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the petition.
     *
     * @param createdAt Creation date of the petition.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the validity period of the petition.
     *
     * @return Validity period of the petition.
     */
    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * Sets the validity period of the petition.
     *
     * @param validUntil Validity period of the petition.
     */
    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * Retrieves the validity status of the petition.
     *
     * @return Validity status of the petition.
     */
    public Boolean getIsValid() {
        return isValid;
    }

    /**
     * Sets the validity status of the petition.
     *
     * @param isValid Validity status of the petition.
     */
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Sets the creation and validity dates of the petition before persisting.
     */
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        validUntil = createdAt.plusDays(30L);
    }

    /**
     * Retrieves the company associated with the petition.
     *
     * @return Company associated with the petition.
     */
    public CompanyUser getCompany() {
        return company;
    }

    /**
     * Sets the company associated with the petition.
     *
     * @param company Company associated with the petition.
     */
    public void setCompany(CompanyUser company) {
        this.company = company;
    }

    /**
     * Retrieves the list of users who voted for the petition.
     *
     * @return List of users who voted for the petition.
     */
    public List<SimpleUser> getVoters() {
        return voters;
    }

    /**
     * Sets the list of users who voted for the petition.
     *
     * @param voters List of users who voted for the petition.
     */
    public void setVoters(List<SimpleUser> voters) {
        this.voters = voters;
    }

    /**
     * Retrieves the validity status of the petition.
     *
     * @return Validity status of the petition.
     */
    public Boolean getValid() {
        return isValid;
    }

    /**
     * Sets the validity status of the petition.
     *
     * @param valid Validity status of the petition.
     */
    public void setValid(Boolean valid) {
        isValid = valid;
    }

    /**
     * Overrides the default equals method to compare two petitions based on their IDs.
     *
     * @param obj Object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractPetition){
            return ((AbstractPetition) obj).id.equals(this.id);
        }
        return false;
    }

    /**
     * Implements the Comparable interface to enable comparing petitions based on their IDs.
     *
     * @param o Another AbstractPetition object to compare with.
     * @return Comparison result based on the IDs.
     */
    @Override
    public int compareTo(@NonNull AbstractPetition o) {
        return this.id.compareTo(o.id);
    }
}