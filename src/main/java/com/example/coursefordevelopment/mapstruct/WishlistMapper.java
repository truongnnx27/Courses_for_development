package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.WishlistDto;
import com.example.coursefordevelopment.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WishlistMapper {

    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    WishlistDto toWishlistDto(Wishlist wishlist);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId",target = "course.id")
    Wishlist toWishlist(WishlistDto wishlistDto);
}
