package coursefordevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
<<<<<<<< HEAD:src/main/java/coursefordevelopment/dto/LectureDto.java
public class LectureDto {
    private Long id;
    private String title;
    private String type;
    private List<VideoDto> videos;
    private QuizDto quiz;
========
public class SectionDto {
    private Long id;
    private String title;
    private List<LectureDto> lectures = new ArrayList<>();
>>>>>>>> truongdev:src/main/java/coursefordevelopment/dto/SectionDto.java
}
