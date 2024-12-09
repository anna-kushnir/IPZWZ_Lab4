package b41.lab4.data.nosql;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sellers")
@Getter
@Setter
public class Seller {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String region;
}
