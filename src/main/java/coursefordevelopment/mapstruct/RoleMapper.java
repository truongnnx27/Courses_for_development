package coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.RoleDto;
import com.example.coursefordevelopment.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto roleToRoleDto(Role role);
    Role roleDtoToRole(RoleDto roleDto);
}
