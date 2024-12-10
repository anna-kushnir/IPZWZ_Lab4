package b41.lab4.controller;

import b41.lab4.data.nosql.Seller;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.nosql.SellerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    private final SellerRepository sellerRepository;

    public SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Seller>> filterSellers(@RequestParam String region) {
        List<Seller> sellers = sellerRepository.findByRegion(region);
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable String id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        boolean exists = sellerRepository.existsById(seller.getId());
        if (exists) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Конфлікт, якщо продавець вже існує
        }
        Seller savedSeller = sellerRepository.save(seller);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable String id, @RequestBody Seller updatedSeller) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
        seller.setName(updatedSeller.getName());
        seller.setAge(updatedSeller.getAge());
        seller.setGender(updatedSeller.getGender());
        seller.setRegion(updatedSeller.getRegion());
        Seller savedSeller = sellerRepository.save(seller);
        return new ResponseEntity<>(savedSeller, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable String id) {
        if (!sellerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seller not found");
        }
        sellerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
