package cum.MyRH.Models.Entities;
import cum.MyRH.Models.Enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applicant")
public class Applicant {
    public Applicant(Long id, Files cv) {
        this.id = id;
        this.cv = cv;
    }

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

    @ManyToOne
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    private Files cv;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private State state;

    @ManyToMany
    private List<Badge> badges;

    @OneToMany(mappedBy = "applicant")
    private List<ApplicantTest> applicantTests;

    public Applicant(Long id, String firstName, String lastName, String email, String password, String level, String profile, String city, State state) {
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

    public Applicant(long id, String name, String lastName, String email, String encode, String level, String profile, String city, Files cv, State state) {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.password = encode;
        this.level = level;
        this.profile = profile;
        this.city = city;
        this.cv = cv;
        this.state = state;
    }
}