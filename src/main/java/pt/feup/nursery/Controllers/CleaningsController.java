package pt.feup.nursery.Controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pt.feup.nursery.cleanings.CleaningService;

import javax.validation.Valid;

@RestController
public class CleaningsController {

    private static final Logger logger = LogManager.getLogger(CleaningsController.class);

    @Autowired
    private CleaningService cleaningService = new CleaningService();


    // Cleaning end-points
    @GetMapping("/cleanings")
    public ResponseEntity<Object> getNurseriesCleanings() {
        try {
            return ResponseEntity.ok().body(cleaningService.getNurseriesCleanings());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list Cleanings of Nurseries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cleanings/{id}")
    public ResponseEntity<Object> getCleaning(final @PathVariable String id) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.getCleaning(id).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of Cleanings", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/cleanings/{id}")
    public ResponseEntity<Object> deleteCleaning(final @PathVariable String id) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.deleteCleaning(id).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to delete Cleaning", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cleanings")
    public ResponseEntity<Object> addCleaning(@Valid @RequestBody String body) {
        try {
            return ResponseEntity.ok().body(cleaningService.addCleaning(body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to add Cleaning", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cleanings/{id}")
    public ResponseEntity<Object> updateCleaning(final @PathVariable String id, final @Valid @RequestBody String body) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.updateCleaning(id,body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to update cleaning", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cleanings/{id}/workers")
    public ResponseEntity getWorkersFromCleaning(final @PathVariable String id) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.getWorkersFromCleaning(id).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of workers from cleanings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cleanings/availableworkers")
    public ResponseEntity getAvailableWorkers() {
        try {
            return ResponseEntity.ok().body(cleaningService.getAvailableWorkers().getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of Available Workers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
    @PostMapping("/workers")
    public ResponseEntity createCleaningWorker(final @Valid @RequestBody String body) {
        try {
            return ResponseEntity.ok().body(cleaningService.createCleaningWorker(body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to create worker ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping("/cleanings/{id}/workers")
    public ResponseEntity addWorkerToCleaning(final @PathVariable String id,final @Valid @RequestBody String body) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.addWorkerToCleaning(id, body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to add worker ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cleanings/{id}/products")
    public ResponseEntity getStock(final @PathVariable String id) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.getStock(id).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of Products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cleanings/{id}/products")
    public ResponseEntity requestStock(@PathVariable String id, final @Valid @RequestBody String body) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.requestStock(id, body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to request product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cleanings/{id}/products")
    public ResponseEntity consumeStock(@PathVariable String id, final @Valid @RequestBody String body) {
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(cleaningService.consumeStock(id,body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to consume product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        @PostMapping("/products/notifications")
    public ResponseEntity notifications(final @Valid @RequestBody String body) {
        try {
            return ResponseEntity.ok().body(cleaningService.notifications(body).getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to notificate cleanings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/employees")
    public ResponseEntity getEmployees() {
        try {
            return ResponseEntity.ok().body(cleaningService.getEmployees().getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of Employees", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
