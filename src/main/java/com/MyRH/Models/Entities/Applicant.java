package com.MyRH.Models.Entities;
import com.MyRH.Models.Enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applicant")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private String level;

    @Column(name = "profile")
    private String profile;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    private Files cv;

    @Column(name = "state")
    private State state;

    public Applicant(Long id, String firstName, String lastName, String email, String password, String level, String profile, String city, State state){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.level = level;
        this.profile = profile;
        this.city = city;
        this.state = state;
    }
}
