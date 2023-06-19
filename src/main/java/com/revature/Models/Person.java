package com.revature.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Person {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    private String Location;
    //many people can be an employee or finance manager
    @ManyToOne
    private Role role;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany
    private List<Reimbursement> reimbursements;

    //boiler plate code


    public Person() {
    }

    public Person(int id, String firstname, String lastname, String location, Role role, String username, String password, List<Reimbursement> reimbursements) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        Location = location;
        this.role = role;
        this.username = username;
        this.password = password;
        this.reimbursements = reimbursements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Reimbursement> getReimbursements() {
        return reimbursements;
    }

    public void setReimbursements(List<Reimbursement> reimbursements) {
        this.reimbursements = reimbursements;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", Location='" + Location + '\'' +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", reimbursements=" + reimbursements +
                '}';
    }
}
