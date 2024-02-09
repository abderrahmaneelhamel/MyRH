package cum.MyRH.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "applicant_test")
public class ApplicantTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private int attempts;

    private boolean passed;
}
