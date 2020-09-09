package com.sanatorium.sanatorium.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Entity
@Table(name = "ROOMS")
@EnableAutoConfiguration
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    private int floor;

    private int number;

    @Column(nullable = true)
    private String state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    /**
     * Przeciążona metoda toString, definiująca w jaki sposób obiekt powinien być reprezentowany przez ciąg znaków
     * @return ciąg znaków opisujący obiekt
     */
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", floor=" + floor +
                ", number=" + number +
                ", state='" + state + '\'' +
                '}';
    }
}
