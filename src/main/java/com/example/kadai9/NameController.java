package com.example.kadai9;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class NameController {
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    @GetMapping("/names")
    public List<Name> names() {
        return nameService.findAll();
    }

    @GetMapping("/names/{id}")
    public Name findName(@PathVariable(name ="id") int id) {
        return nameService.findName(id);
    }
}
