package ru.controllers;

import dtos.main.compilation.CompilationDto;
import dtos.main.compilation.NewCompilationDto;
import dtos.main.request.UpdateCompilationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.exceptions.NotFoundException;
import ru.services.compilation.CompilationService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping("/compilations")
    public ResponseEntity<List<CompilationDto>> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                                @RequestParam(required = false, defaultValue = "0") int from,
                                                                @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(compilationService.getCompilations(pinned, from, size), HttpStatus.OK);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable("compId") long compId) throws NotFoundException {
        return new ResponseEntity<>(compilationService.getCompilationById(compId), HttpStatus.OK);
    }

    @PostMapping("/admin/compilations")
    public ResponseEntity<CompilationDto> createCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return new ResponseEntity<>(compilationService.createCompilation(newCompilationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> removeCompilationById(@PathVariable("compId") long compId) throws NotFoundException {
        compilationService.removeCompilationById(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public ResponseEntity<CompilationDto> updateCompilationById(@PathVariable("compId") long compId,
                                                                @RequestBody UpdateCompilationRequest updateCompilationRequest) throws NotFoundException {
        return new ResponseEntity<>(compilationService.updateCompilationById(compId, updateCompilationRequest), HttpStatus.OK);
    }
}
