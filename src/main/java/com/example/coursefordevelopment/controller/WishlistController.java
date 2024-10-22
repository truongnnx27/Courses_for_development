package com.example.coursefordevelopment.controller;

import com.example.coursefordevelopment.dto.Request.ApiRespone;
import com.example.coursefordevelopment.dto.Request.AppException;
import com.example.coursefordevelopment.dto.WishlistDto;
import com.example.coursefordevelopment.entity.Wishlist;
import com.example.coursefordevelopment.service.WishlistService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/wishlist")
public class WishlistController {

    WishlistService wishlistService;

    @PostMapping("/addWishlist")
    public ResponseEntity<ApiRespone<WishlistDto>> createWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto createWishlist = wishlistService.addWishlist(wishlistDto.getUserId(), wishlistDto.getCourseId());
        ApiRespone<WishlistDto> apiRespone = new ApiRespone<>(9898, "Wishlist added successfully", createWishlist);

        return ResponseEntity.ok(apiRespone);
    }

    @GetMapping("/getWishlistForUser/{userId}")
    public ResponseEntity<ApiRespone<List<WishlistDto>>> getWishlistForUser(@PathVariable Long userId)
    {
        List<WishlistDto> wishlistDtos = wishlistService.getWishlistForUser(userId);
        ApiRespone<List<WishlistDto>> apiRespone =  new ApiRespone<>(9898,"Wishlist fetched successfully",wishlistDtos);
        return ResponseEntity.ok(apiRespone);
    }

    @DeleteMapping("/delete/{userId}/{courseId}")
    public ResponseEntity<ApiRespone<Long>> deleteWishlist(@PathVariable Long userId, @PathVariable Long courseId)
    {
        try {
            wishlistService.removeWishlist(userId,courseId);
            ApiRespone<Long> apiRespone = new ApiRespone<>(9898,"Wishlist deleted successfully",userId);
            return ResponseEntity.ok(apiRespone);
        }catch (AppException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiRespone<>(9999, "User or course not found", null));
        }

    }

}
