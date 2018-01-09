package com.beacon.mapper;

import com.beacon.entity.User;
import com.beacon.pojo.UserInfoDto;
import org.mapstruct.Mapper;

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

}
