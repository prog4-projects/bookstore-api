package prog.hei.app.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
  private String email;
  private String address;
}
