package b41.lab4.controller;

import b41.lab4.data.nosql.Seller;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.nosql.SellerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @TODO: повертати ResponseEntity зі статус кодами та відповідним Seller як результат усіх запитів
@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    private final SellerRepository sellerRepository;

    public SellerController(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @GetMapping("/filter")
    public List<Seller> filterSellers(@RequestParam String region) {
        return sellerRepository.findByRegion(region);
    }

    @GetMapping("/{id}")
    public Seller getSellerById(@PathVariable String id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
    }

    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        // @TODO: додати перевірку, чи продавець вже існує
        return sellerRepository.save(seller);
    }

    @PutMapping("/{id}")
    public Seller updateSeller(@PathVariable String id, @RequestBody Seller updatedSeller) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
        seller.setName(updatedSeller.getName());
        seller.setAge(updatedSeller.getAge());
        seller.setGender(updatedSeller.getGender());
        seller.setRegion(updatedSeller.getRegion());
        return sellerRepository.save(seller);
    }

    @DeleteMapping("/{id}")
    public void deleteSeller(@PathVariable String id) {
        sellerRepository.deleteById(id);
    }
}
