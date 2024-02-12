package cum.MyRH.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cum.MyRH.Models.Enums.AnswerStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    public Answer(Long id, String content, AnswerStatus status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnoreProperties({"answers", "test"})
    private Question question;

    private String content;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private AnswerStatus status;

}