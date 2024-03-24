package kg.talantova.shoppingcart.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
@Tag(
        name = "Контроллер для приветствия",
        description = "В этом контроллере есть один метод для получения фразы hello"
)
public class HelloController {

    @GetMapping
    @Operation(
            summary = "Получить фразу hello "
    )
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Hello from Dilbara", HttpStatus.OK);
    }
}
