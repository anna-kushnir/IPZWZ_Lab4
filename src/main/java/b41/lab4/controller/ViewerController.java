package b41.lab4.controller;

import b41.lab4.data.Viewer;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.ViewerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viewers")
public class ViewerController {
    private final ViewerRepository viewerRepository;

    public ViewerController(ViewerRepository viewerRepository) {
        this.viewerRepository = viewerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Viewer>> getAllViewers() {
        List<Viewer> viewers = viewerRepository.findAll();
        return new ResponseEntity<>(viewers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viewer> getViewerById(@PathVariable Long id) {
        Viewer viewer = viewerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viewer not found"));
        return new ResponseEntity<>(viewer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Viewer> createViewer(@RequestBody Viewer viewer) {
        Viewer savedViewer = viewerRepository.save(viewer);
        return new ResponseEntity<>(savedViewer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viewer> updateViewer(@PathVariable Long id, @RequestBody Viewer updatedViewer) {
        Viewer viewer = viewerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viewer not found"));

        viewer.setName(updatedViewer.getName());
        viewer.setAge(updatedViewer.getAge());
        viewer.setGender(updatedViewer.getGender());

        Viewer savedViewer = viewerRepository.save(viewer);
        return new ResponseEntity<>(savedViewer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViewer(@PathVariable Long id) {
        if (!viewerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Viewer not found");
        }
        viewerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
