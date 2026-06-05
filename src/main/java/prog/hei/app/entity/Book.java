package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

  @Check(constraints = "purchasePrice >= 0")
  private Double purchasePrice;

  @Check(constraints = "sellingPrice = 0")
  private Double sellingPrice;

  private LocalDate publicationDate;

  @ManyToMany private List<Library> library;

  private List<Author> authors;
}
