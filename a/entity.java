//package belirt

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class entity {
    @Id
    @gebeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public BigDecimal price;
    public String description;
}
