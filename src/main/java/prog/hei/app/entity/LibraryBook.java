package prog.hei.app.entity;

import jakarta.persistence.*;
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

  private int stock;

  @ManyToOne
  @JoinColumn(name = "library_id")
  private Library library;

  @ManyToOne
  @JoinColumn(name = "book_edition_id")
  private BookEdition bookEdition;
}
