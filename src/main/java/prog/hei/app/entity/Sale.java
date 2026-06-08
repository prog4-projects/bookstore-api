package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
@Table(name = "sale")
public class Sale {

  @Id @GeneratedValue private UUID id;

  @Enumerated(EnumType.STRING)
  private SaleStatusEnum status;

  @Column(nullable = false)
  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "sale")
  private List<SaleItem> saleItems;

  @OneToMany(mappedBy = "sale")
  private List<Payment> payments;
}
