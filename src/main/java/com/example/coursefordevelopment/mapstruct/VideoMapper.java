package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.VideoDto;
import com.example.coursefordevelopment.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    Video videoDtoToVideo(VideoDto videoDto);

    VideoDto videoToVideoDto(Video video);
}
