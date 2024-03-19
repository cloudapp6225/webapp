package com.cloudnativewebapp.webapp.Controller;

import com.cloudnativewebapp.webapp.Service.HealthCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;

    @Autowired
    private HttpServletRequest request;
    HttpHeaders header;

    Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    public HealthCheckController() {
        header = new HttpHeaders();
        header.set("Cache-Control", "no-cache, no-store, must-revalidate;");
        header.set("Pragma", "no-cache");
        header.set("X-Content-Type-Options", "nosniff");
    }

    @GetMapping("/healthz")
    public ResponseEntity<Void> checkDBHealth(@RequestBody(required = false)Object body, @RequestParam(required = false) Object par) {
        if(body != null || request.getQueryString() != null || par != null) {
            logger.error("Invalid body in health check");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(header).build();
        }

        if(healthCheckService.isDBConnected()) {
            logger.debug("healthz debug");
            logger.info("Database is connected");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(header).build();
        }
        else {
            logger.error("Database service is unavailable");
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .headers(header).build();
        }
    }

    @PutMapping("/healthz")
    public ResponseEntity<Void> putDBHealth() {
        logger.warn("Put request is not allowed");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @PostMapping("/healthz")
    public ResponseEntity<Void> postDBHealth() {
        logger.warn("Post request is not allowed");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @DeleteMapping("/healthz")
    public ResponseEntity<Void> deleteDBHealth() {
        logger.warn("Delete request is not allowed");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @PatchMapping("/healthz")
    public ResponseEntity<Void> patchDBMapping() {
        logger.warn("Patch request is not allowed");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @RequestMapping(value = "/healthz", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headDBMapping() {
        logger.warn("request is not allowed");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @RequestMapping(value = "/healthz", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsDBMapping() {
        logger.warn("request is not allowed");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }


}
