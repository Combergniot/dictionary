package com.gus.translations.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor

@Entity
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String polishTerm;
    private String englishTerm;

    @Lob
    private String definition;

    @Lob
    private String englishDefinition;

    @Lob
    private String methodologicalExplanation;

    @Lob
    private String englishMethodologicalExplanation;

    private String domain;

    @Lob
    private String source;

    @Lob
    private String url;

    private String responsiblePerson;

    private String email;


    public Dictionary() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolishTerm() {
        return polishTerm;
    }

    public void setPolishTerm(String polishTerm) {
        this.polishTerm = polishTerm;
    }

    public String getEnglishTerm() {
        return englishTerm;
    }

    public void setEnglishTerm(String englishTerm) {
        this.englishTerm = englishTerm;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getEnglishDefinition() {
        return englishDefinition;
    }

    public void setEnglishDefinition(String englishDefinition) {
        this.englishDefinition = englishDefinition;
    }

    public String getMethodologicalExplanation() {
        return methodologicalExplanation;
    }

    public void setMethodologicalExplanation(String methodologicalExplanation) {
        this.methodologicalExplanation = methodologicalExplanation;
    }

    public String getEnglishMethodologicalExplanation() {
        return englishMethodologicalExplanation;
    }

    public void setEnglishMethodologicalExplanation(String englishMethodologicalExplanation) {
        this.englishMethodologicalExplanation = englishMethodologicalExplanation;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
