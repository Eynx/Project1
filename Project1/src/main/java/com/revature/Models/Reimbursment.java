package com.revature.Models;

import javax.persistence.*;

@Entity
@Table(name = "reimbursments")
public class Reimbursment {
    @Id
    @Column(name = "reimbursment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;

    private String description;

    private int amount;
    //boiler

    public Reimbursment() {
    }

    public Reimbursment(int id, String name, String description, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Reimbursment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
