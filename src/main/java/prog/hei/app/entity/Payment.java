package prog.hei.app.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
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
@Table(name = "payment")
public class Payment {

  @Id @GeneratedValue private UUID id;

  @Enumerated(EnumType.STRING)
  private PaymentMethodEnum type;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private MobileMoneyProviderEnum mobileMoneyProvider;

  @Column(unique = true)
  private String transactionReference;

  @Enumerated(EnumType.STRING)
  private PaymentStatusEnum status;

  @Column(nullable = false)
  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "sale_id", nullable = false)
  private Sale sale;
}
