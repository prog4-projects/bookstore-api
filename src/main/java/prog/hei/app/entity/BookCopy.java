package prog.hei.app.entity;

import jakarta.persistence.*;
import lombok.*;
import prog.hei.app.entity.enums.BookStatusEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BookCopy {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true, nullable = false)
  private String code;

  @Enumerated(EnumType.STRING)
  private BookStatusEnum status;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "arrival_id")
  private Arrival arrival;
}
