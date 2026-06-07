package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prog.hei.app.entity.enums.SaleStatusEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private Double totalAmount;

  @Enumerated(EnumType.STRING)
  private SaleStatusEnum status;

  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "sale")
  private List<BookCopy> bookCopies;

  @OneToMany(mappedBy = "sale")
  private List<Payment> payments;
}
