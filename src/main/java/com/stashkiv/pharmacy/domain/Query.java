package com.stashkiv.pharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Query.
 */
@Entity
@Table(name = "query")
public class Query implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "query", nullable = false)
    private String query;

    @Column(name = "answer")
    private String answer;

    @OneToOne
    @JoinColumn(unique = true)
    private Doctor answeredBy;

    @ManyToOne
    @JsonIgnoreProperties("queries")
    private Patient askedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public Query query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnswer() {
        return answer;
    }

    public Query answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Doctor getAnsweredBy() {
        return answeredBy;
    }

    public Query answeredBy(Doctor doctor) {
        this.answeredBy = doctor;
        return this;
    }

    public void setAnsweredBy(Doctor doctor) {
        this.answeredBy = doctor;
    }

    public Patient getAskedBy() {
        return askedBy;
    }

    public Query askedBy(Patient patient) {
        this.askedBy = patient;
        return this;
    }

    public void setAskedBy(Patient patient) {
        this.askedBy = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Query)) {
            return false;
        }
        return id != null && id.equals(((Query) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Query{" +
            "id=" + getId() +
            ", query='" + getQuery() + "'" +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
