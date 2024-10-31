package com.example.coursefordevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopIntructorResponse {
    private String id;
    private String fullname;
    private String avatarUrl;
    private Long numberUserPayment;
}
