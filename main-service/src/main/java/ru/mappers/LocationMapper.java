package ru.mappers;

import dtos.main.LocationDto;
import ru.models.Location;

public class LocationMapper {
    public static LocationDto toLocationDto(Location location) {
        return new LocationDto(
                location.getLat(),
                location.getLon()
        );
    }

    public static Location toLocation(LocationDto locationDto) {
        return new Location(
                0,
                locationDto.getLat(),
                locationDto.getLon()
        );
    }
}
