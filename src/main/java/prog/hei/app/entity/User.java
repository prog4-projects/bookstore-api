package prog.hei.app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.*;
import prog.hei.app.entity.enums.UserGenderEnum;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String firstName;
  private String lastName;
  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  private UserGenderEnum gender;
}
