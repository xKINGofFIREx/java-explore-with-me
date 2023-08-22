package ru.services.compilation;

import dtos.main.compilation.CompilationDto;
import dtos.main.compilation.NewCompilationDto;
import dtos.main.request.UpdateCompilationRequest;
import ru.exceptions.NotFoundException;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(long compId) throws NotFoundException;

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void removeCompilationById(long compId) throws NotFoundException;

    CompilationDto updateCompilationById(long compId, UpdateCompilationRequest updateCompilationRequest) throws NotFoundException;
}
