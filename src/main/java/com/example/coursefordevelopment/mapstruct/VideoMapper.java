package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.VideoDto;
import com.example.coursefordevelopment.entity.Video;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



public interface VideoMapper {
    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "lesson.id", target = "lessonId")
    VideoDto videoToVideoDto(Video video);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "lessonId", target = "lesson.id")
    Video videoDtoToVideo(VideoDto videoDto);
}
