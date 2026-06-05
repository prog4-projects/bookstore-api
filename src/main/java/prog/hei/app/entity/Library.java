package prog.hei.app.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Library {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;
  private String address;

  @Check(constraints = "LENGTH(phoneNumber) >= 10 AND LENGTH(phoneNumber) <= 15")
  private String phoneNumber;

  @ManyToMany() private List<Book> books;
}
