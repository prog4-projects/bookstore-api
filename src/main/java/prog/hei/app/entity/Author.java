package prog.hei.app.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "author")
public class Author extends User {
  @OneToMany(mappedBy = "author")
  private List<AuthorBook> authorBooks;
}
