package ru.mappers;

import dtos.main.request.ParticipationRequestDto;
import ru.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }

    public static List<ParticipationRequestDto> toParticipationRequestDtos(List<Request> requests) {
        List<ParticipationRequestDto> participationRequestDtos = new ArrayList<>();
        for (Request request : requests)
            participationRequestDtos.add(RequestMapper.toParticipationRequestDto(request));
        return participationRequestDtos;
    }
}
