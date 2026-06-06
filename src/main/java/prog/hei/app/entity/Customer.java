package prog.hei.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
  private String email;
  private String address;

  @OneToMany(mappedBy = "customer")
  private List<Sale> sales;
}
