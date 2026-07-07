package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "arrival")
public class Arrival {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private LocalDate date;

  @Column(columnDefinition = "TEXT")
  private String description;

  @OneToMany(mappedBy = "arrival")
  private List<ArrivalItem> arrivalItems;

  @OneToMany(mappedBy = "arrival")
  private List<StockMovement> stockMovements;
}
