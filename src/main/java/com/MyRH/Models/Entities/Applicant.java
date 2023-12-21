package com.MyRH.Models.Entities;
import jakarta.persistence.*;
import lombok.*;

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
    private String firstnName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "message")
    private String message;

    @Column(name = "level")
    private String level;

    @Column(name = "profile")
    private String profile;

    @Column(name = "city")
    private String city;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Job_id", referencedColumnName = "id")
    private Job job;
}
