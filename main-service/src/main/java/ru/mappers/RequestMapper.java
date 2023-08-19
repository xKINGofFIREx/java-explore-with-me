package ru.mappers;

import dtos.main.request.ParticipationRequestDto;
import ru.models.Request;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(

        );
    }
}
