package ru.mappers;

import dtos.main.compilation.CompilationDto;
import dtos.main.compilation.NewCompilationDto;
import ru.models.Compilation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto compilationDto) {
        return new Compilation(
                0,
                compilationDto.isPinned(),
                compilationDto.getTitle(),
                new ArrayList<>()
        );
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle(),
                EventMapper.toEventShortDtos(compilation.getEvents())
        );
    }

    public static List<CompilationDto> toCompilationDtos(List<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }
}
