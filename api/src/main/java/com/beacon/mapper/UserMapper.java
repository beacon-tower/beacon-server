package com.beacon.mapper;

import com.beacon.entity.User;
import com.beacon.pojo.UserInfoDto;
import com.beacon.pojo.UserShowDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 用户dto转换
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@Mapper(componentModel = "jsr330")
public abstract class UserMapper {

    public abstract User fromInfoDto(UserInfoDto userInfoDto);

    public abstract UserInfoDto toInfoDto(User user);

    @Mappings({
            @Mapping(source = "avatarImg.url", target = "avatarUrl"),
    })
    public abstract UserShowDto toShowDto(User user);
}
