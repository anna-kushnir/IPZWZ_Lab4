package b41.lab4.repository.nosql;

import b41.lab4.data.nosql.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SellerRepository extends MongoRepository<Seller, String> {
    List<Seller> findByRegion(String region);
}
