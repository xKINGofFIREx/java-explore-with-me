package ru.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class CompilationController {

    @GetMapping("/compilations")
    public void getCompilations() {

    }

    @GetMapping("/compilations/{compId}")
    public void getCompilationById(@PathVariable("compId") long compId) {

    }

    @PostMapping("/admin/compilations")
    public void createCompilation() {

    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void removeCompilationById(@PathVariable("compId") long compId) {

    }

    @PatchMapping("/admin/compilations/{compId}")
    public void updateCompilationById(@PathVariable("compId") long compId) {

    }
}
