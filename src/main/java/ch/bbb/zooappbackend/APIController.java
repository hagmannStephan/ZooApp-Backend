package ch.bbb.zooappbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @GetMapping("/api/data")
    public ResponseEntity<String> fetchData() {
        // Your backend logic to fetch data
        String data = "Hello from Java Backend!";
        return ResponseEntity.ok(data);
    }

}
