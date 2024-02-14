package cum.MyRH.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test")
public class Test {
    public Test(Long id, String name,Badge badge) {
        this.id = id;
        this.name = name;
        this.badge = badge;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "test",fetch = FetchType.LAZY)
    private List<Question> questions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "badge_id")
    private Badge badge;
}
