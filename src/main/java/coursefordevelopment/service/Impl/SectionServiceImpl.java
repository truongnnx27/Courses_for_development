package coursefordevelopment.service.Impl;

import com.example.coursefordevelopment.dto.SectionDto;
import com.example.coursefordevelopment.entity.Section;
import com.example.coursefordevelopment.mapstruct.SectionMapper;
import com.example.coursefordevelopment.repository.SectionRepository;
import com.example.coursefordevelopment.service.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper = SectionMapper.INSTANCE;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public SectionDto createSection(SectionDto sectionDto) {
        Section section = sectionMapper.sectionDtoToSection(sectionDto);
        section = sectionRepository.save(section);
        return sectionMapper.sectionToSectionDto(section);
    }

    @Override
    public SectionDto getSectionById(Long id) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new RuntimeException("Section not found"));
        return sectionMapper.sectionToSectionDto(section);
    }

    @Override
    public List<SectionDto> getAllSections() {
        return sectionRepository.findAll().stream()
                .map(sectionMapper::sectionToSectionDto)
                .collect(Collectors.toList());
    }

    @Override
    public SectionDto updateSection(Long id, SectionDto sectionDto) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new RuntimeException("Section not found"));
        section.setTitle(sectionDto.getTitle());
        section = sectionRepository.save(section);
        return sectionMapper.sectionToSectionDto(section);
    }

    @Override
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
