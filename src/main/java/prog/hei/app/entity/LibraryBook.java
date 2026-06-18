package prog.hei.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "library_book")
public class LibraryBook {

  @Id @GeneratedValue private UUID id;

  @Min(0)
  private int stock;

  @ManyToOne
  @JoinColumn(name = "library_id")
  private Library library;

  @ManyToOne
  @JoinColumn(name = "book_edition_id")
  private BookEdition bookEdition;
}
