package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import prog.hei.app.entity.enums.MovementTypeEnum;
import prog.hei.app.entity.enums.ReasonTypeEnum;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "stock_movement")
public class StockMovement {

  @Id @GeneratedValue private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MovementTypeEnum type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReasonTypeEnum reason;

  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false)
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "book_edition_id", nullable = false)
  private BookEdition bookEdition;

  @ManyToOne
  @JoinColumn(name = "arrival_id")
  private Arrival arrival;

  @ManyToOne
  @JoinColumn(name = "sale_id")
  private Sale sale;
}
