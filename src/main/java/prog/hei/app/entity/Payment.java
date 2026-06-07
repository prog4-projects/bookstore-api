package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prog.hei.app.entity.enums.MobileMoneyProviderEnum;
import prog.hei.app.entity.enums.PaymentMethodEnum;
import prog.hei.app.entity.enums.PaymentStatusEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Enumerated(EnumType.STRING)
  private PaymentMethodEnum type;

  private Double amount;

  @Enumerated(EnumType.STRING)
  private MobileMoneyProviderEnum mobileMoneyProvider;

  @Column(unique = true)
  private String transactionReference;

  @Enumerated(EnumType.STRING)
  private PaymentStatusEnum status;

  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "sale_id", nullable = false)
  private Sale sale;
}
