package prog.hei.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.*;
import prog.hei.app.entity.enums.UserGenderEnum;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class User {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserGenderEnum gender;
}
