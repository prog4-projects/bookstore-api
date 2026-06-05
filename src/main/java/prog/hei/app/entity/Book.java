package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Check;
import prog.hei.app.entity.enums.BookFormatEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String isbn;
  private String title;
  private String publisher;

  @Check(constraints = "pages > 0")
  private int pages;

  @Enumerated(EnumType.STRING)
  private BookFormatEnum format;

  @Check(constraints = "purchasePrice >= 0")
  private Double purchasePrice;

  @Check(constraints = "sellingPrice >= 0")
  private Double sellingPrice;

  private LocalDate publicationDate;

  @OneToMany(mappedBy = "book")
  private List<AuthorBook> authorBooks;

  @OneToMany(mappedBy = "book")
  private List<LibraryBook> libraryBooks;
}
