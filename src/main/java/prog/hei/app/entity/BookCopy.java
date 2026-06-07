package prog.hei.app.entity;

import jakarta.persistence.*;
import lombok.*;
import prog.hei.app.entity.enums.BookStatusEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCopy {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Enumerated(EnumType.STRING)
  private BookStatusEnum status;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "arrival_id")
  private Arrival arrival;

  @ManyToOne
  @JoinColumn(name = "sale_id")
  private Sale sale;
}
