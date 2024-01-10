package com.MyRH.Models.Entities;
import com.MyRH.Models.Enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Image_id", referencedColumnName = "id")
    private Files image;

    @ManyToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private Plan plan;

    @Column(name = "state")
    private State state;

    public Company(Long id, String name, String email, String password, String address, String phone,Plan plan,State state) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.plan = plan;
        this.state = state;
    }
}
