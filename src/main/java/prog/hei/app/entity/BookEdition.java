package prog.hei.app.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book_edition")
public class BookEdition {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false, unique = true)
  private String isbn;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BookLanguageEnum language;

  @Enumerated(EnumType.STRING)
  private BookFormatEnum format;

  @Column(nullable = false)
  private Integer pageCount;

  private String publisher;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private LocalDateTime publicationDate;

  @ManyToOne
  @JoinColumn(name = "book_id", nullable = false)
  private Book book;

  @OneToMany(mappedBy = "bookEdition", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<ArrivalItem> arrivalItems;

  @OneToMany(mappedBy = "bookEdition", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<LibraryBook> libraryBooks;
}
