package prog.hei.app.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Library {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;
  private String address;

  @Check(constraints = "LENGTH(phoneNumber) >= 10 AND LENGTH(phoneNumber) <= 15")
  private String phoneNumber;

  @OneToMany(mappedBy = "library")
  private List<LibraryBook> libraryBooks;
}
