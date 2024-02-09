package cum.MyRH.Models.Entities;

import cum.MyRH.Models.Enums.AnswerStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String content;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private AnswerStatus status;

}