package prog.hei.app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Arrival {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private LocalDate date;
  private String description;

  @OneToMany(mappedBy = "arrival")
  private List<BookCopy> bookCopies;
}
