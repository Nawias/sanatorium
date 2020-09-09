package com.sanatorium.sanatorium.models;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "VISITS")
@EnableAutoConfiguration
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @Column(nullable = true)
    public boolean active;


    @OneToOne
    private User patient;

    @OneToOne
    private User doctor;

    @OneToOne
    @Nullable
    private Referral referral;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Referral getReferral() {
        return referral;
    }

    public void setReferral(Referral referral) {
        this.referral = referral;
    }
    /**
     * Metoda konwertująca datę w obiekcie na format obsługiwany przez HTML
     * @return ciąg znaków w formacie DateTime obsługiwanym przez HTML
     */
    public String getDateTimeInHtmlFormat() {

        String date = dateTime.toString();
        date = date.substring(0,10)+"T"+date.substring(11,16);
        return date;
    }
    /**
     * Metoda konwertująca datę w obiekcie na format przyjazny użytkownikowi
     * @return ciąg znaków w formacie DateTime (data i czas) przyjaznym użytkownikowi
     */
    public String getDateTimeInUserFriendlyFormat(){
        String date = dateTime.toString();
        date = date.substring(0,10)+" Godz.: "+date.substring(11,16);
        return date;
    }

    public String getDateInUserFriendlyFormat(){
        String date = dateTime.toString();
        date = date.substring(0,10);
        return date;
    }
}
