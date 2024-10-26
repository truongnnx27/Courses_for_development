package coursefordevelopment.service;

import com.example.coursefordevelopment.dto.SectionDto;

import java.util.List;

public interface SectionService {
    SectionDto createSection(SectionDto sectionDto);
    SectionDto getSectionById(Long id);
    List<SectionDto> getAllSections();
    SectionDto updateSection(Long id, SectionDto sectionDto);
    void deleteSection(Long id);
}
