package prog.hei.app.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "author_book")
public class AuthorBook {

  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
}
