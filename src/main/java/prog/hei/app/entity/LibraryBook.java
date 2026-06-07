package prog.hei.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryBook {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "library_id")
  private Library library;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
}
