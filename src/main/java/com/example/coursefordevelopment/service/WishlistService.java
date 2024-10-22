package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.dto.Request.AppException;
import com.example.coursefordevelopment.dto.WishlistDto;
import com.example.coursefordevelopment.entity.Course;
import com.example.coursefordevelopment.entity.User;
import com.example.coursefordevelopment.entity.Wishlist;
import com.example.coursefordevelopment.exception.ErrorCode;
import com.example.coursefordevelopment.mapstruct.CourseMapper;
import com.example.coursefordevelopment.mapstruct.UserMapper;
import com.example.coursefordevelopment.mapstruct.WishlistMapper;
import com.example.coursefordevelopment.reponsitory.CourseRepository;
import com.example.coursefordevelopment.reponsitory.UserRepository;
import com.example.coursefordevelopment.reponsitory.WishlistRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WishlistService {

    UserRepository userRepository;
    UserMapper userMapper = UserMapper.INSTANCE;

    CourseRepository courseRepository;
    CourseMapper courseMapper= CourseMapper.INSTANCE;

    WishlistRepository wishlistRepository;
    WishlistMapper wishlistMapper= WishlistMapper.INSTANCE;

    public WishlistDto addWishlist(Long userId, Long courseId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new AppException(ErrorCode.COURSE_NOT_FOUND));

        Optional<Wishlist> existingWishlist = wishlistRepository.findByUserIdAndCourseId(userId,courseId);
        if (existingWishlist.isPresent())
        {
            throw new AppException(ErrorCode.WISHLIST_ALREADY_EXIST);
        }
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setCourse(course);

        Wishlist saveWishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.toWishlistDto(saveWishlist);
    }

    public List<WishlistDto> getWishlistForUser(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Wishlist> wishlistItems = wishlistRepository.findByUser(user);

        return wishlistItems.stream()
                .map(wishlistMapper::toWishlistDto)
                .collect(Collectors.toList());
    }

    public void removeWishlist(Long userId, Long courseId)
    {
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new AppException(ErrorCode.COURSE_NOT_FOUND));

        Wishlist existingWishlist = wishlistRepository.findByUserIdAndCourseId(userId,courseId)
                .orElseThrow(()-> new AppException(ErrorCode.WISHLIST_NOT_FOUND));

        wishlistRepository.delete(existingWishlist);
    }
}
