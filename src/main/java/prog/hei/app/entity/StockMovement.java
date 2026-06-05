package prog.hei.app.entity;

import jakarta.persistence.*;
import lombok.*;
import prog.hei.app.entity.enums.MovementTypeEnum;
import prog.hei.app.entity.enums.ReasonTypeEnum;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private MovementTypeEnum type;

    @Enumerated(EnumType.STRING)
    private ReasonTypeEnum reason;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;
}
