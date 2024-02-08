package cum.MyRH.Models.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skills_id")
    private Test skills;

    private String question;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private List<Answer> answers;
}