package com.cloudnativewebapp.webapp.Controller;

import com.cloudnativewebapp.webapp.Service.HealthCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;

    @Autowired
    private HttpServletRequest request;
    HttpHeaders header;

    public HealthCheckController() {
        header = new HttpHeaders();
        header.set("Cache-Control", "no-cache, no-store, must-revalidate;");
        header.set("Pragma", "no-cache");
        header.set("X-Content-Type-Options", "nosniff");
    }

    @GetMapping("/healthz")
    public ResponseEntity<Void> checkDBHealth(@RequestBody(required = false)Object body, @RequestParam(required = false) Object par) {
        if(body != null || request.getQueryString() != null || par != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(header).build();
        }

        if(healthCheckService.isDBConnected()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(header).build();
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .headers(header).build();
        }
    }

    @PutMapping("/healthz")
    public ResponseEntity<Void> putDBHealth() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @PostMapping("/healthz")
    public ResponseEntity<Void> postDBHealth() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @DeleteMapping("/healthz")
    public ResponseEntity<Void> deleteDBHealth() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @PatchMapping("/healthz")
    public ResponseEntity<Void> patchDBMapping() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @RequestMapping(value = "/healthz", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headDBMapping() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }

    @RequestMapping(value = "/healthz", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsDBMapping() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(header).build();
    }


}
