package prog.hei.app.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "arrival_item")
public class ArrivalItem {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "book_edition_id", nullable = false)
  private BookEdition bookEdition;

  @ManyToOne
  @JoinColumn(name = "arrival_id", nullable = false)
  private Arrival arrival;
}
