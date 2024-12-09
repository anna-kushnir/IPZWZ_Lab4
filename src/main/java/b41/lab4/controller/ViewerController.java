package b41.lab4.controller;

import b41.lab4.data.Viewer;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.ViewerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @TODO: повертати ResponseEntity зі статус кодами та відповідним Viewer як результат усіх запитів
@RestController
@RequestMapping("/api/viewers")
public class ViewerController {
    private final ViewerRepository viewerRepository;

    public ViewerController(ViewerRepository viewerRepository) {
        this.viewerRepository = viewerRepository;
    }

    @GetMapping
    public List<Viewer> getAllViewers() {
        return viewerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Viewer getViewerById(@PathVariable Long id) {
        return viewerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viewer not found"));
    }

    @PostMapping
    public Viewer createViewer(@RequestBody Viewer viewer) {
        return viewerRepository.save(viewer);
    }

    @PutMapping("/{id}")
    public Viewer updateViewer(@PathVariable Long id, @RequestBody Viewer updatedViewer) {
        Viewer viewer = viewerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viewer not found"));
        viewer.setName(updatedViewer.getName());
        viewer.setAge(updatedViewer.getAge());
        viewer.setGender(updatedViewer.getGender());
        return viewerRepository.save(viewer);
    }

    @DeleteMapping("/{id}")
    public void deleteViewer(@PathVariable Long id) {
        viewerRepository.deleteById(id);
    }
}
