package ru.mappers;

import dtos.main.compilation.CompilationDto;
import dtos.main.compilation.NewCompilationDto;
import ru.models.Compilation;

import java.util.ArrayList;
import java.util.List;

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
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation compilation : compilations)
            compilationDtos.add(CompilationMapper.toCompilationDto(compilation));
        return compilationDtos;
    }
}
