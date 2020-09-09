package com.sanatorium.sanatorium.models;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRESCRIPTIONS")
@EnableAutoConfiguration
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToMany
    private List<Medicament> medicament;

    @OneToOne
    private Visit visit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Medicament> getMedicament() {
        return medicament;
    }

    public void setMedicament(List<Medicament> medicament) {
        this.medicament = medicament;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

}
