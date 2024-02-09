package cum.MyRH.Models.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description;

}