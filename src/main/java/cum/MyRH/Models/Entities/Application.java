package cum.MyRH.Models.Entities;
import cum.MyRH.Models.Enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Date date;

    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Applicant applicant;

    private Status status;
}
