package prog.hei.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "library")
public class Library {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private String name;

  private String address;

  @Size(min = 8, max = 15)
  @Pattern(regexp = "^\\d{8,15}$")
  @Column(nullable = false, length = 15)
  private String phoneNumber;

  @OneToMany(mappedBy = "library")
  private List<LibraryBook> libraryBooks;
}
