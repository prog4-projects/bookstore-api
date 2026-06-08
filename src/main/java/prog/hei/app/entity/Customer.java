package prog.hei.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer extends User {

  @Email
  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String address;

  @OneToMany(mappedBy = "customer")
  private List<Sale> sales;
}
