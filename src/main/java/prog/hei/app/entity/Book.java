package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import prog.hei.app.entity.enums.BookGenderEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book")
public class Book {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookGenderEnum gender;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now(); // try Instant
  }

  /*
   * Book (Java) !== JBook (SQL|JPA)
   * ne pas coupler la couche metier et database - il faut les isoler, independant de la database
   * */
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<BookEdition> bookEditions;

  @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<AuthorBook> authorBooks;
}
