package ru.services.compilation;

import dtos.main.compilation.CompilationDto;
import dtos.main.compilation.NewCompilationDto;
import dtos.main.request.UpdateCompilationRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.exceptions.NotFoundException;
import ru.mappers.CompilationMapper;
import ru.models.Compilation;
import ru.models.Event;
import ru.repositories.CompilationRepository;
import ru.repositories.EventRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        if (pinned != null)
            return CompilationMapper.toCompilationDtos(compilationRepository.findAllByPinned(pinned, pageable).toList());
        return CompilationMapper.toCompilationDtos(compilationRepository.findAll(pageable).toList());
    }

    @Override
    public CompilationDto getCompilationById(long compId) throws NotFoundException {
        if (!compilationRepository.existsById(compId))
            throw new NotFoundException("Подборка не найдена или недоступна");
        return CompilationMapper.toCompilationDto(compilationRepository.getReferenceById(compId));
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilation.setEvents(events);
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void removeCompilationById(long compId) throws NotFoundException {
        if (!compilationRepository.existsById(compId))
            throw new NotFoundException("Подборка не найдена или недоступна");
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilationById(long compId,
                                                UpdateCompilationRequest updateCompilationRequest) throws NotFoundException {
        if (!compilationRepository.existsById(compId))
            throw new NotFoundException("Подборка не найдена или недоступна");
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setTitle(updateCompilationRequest.getTitle());
        compilation.setEvents(eventRepository.findAllById(updateCompilationRequest.getEvents()));
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }
}
