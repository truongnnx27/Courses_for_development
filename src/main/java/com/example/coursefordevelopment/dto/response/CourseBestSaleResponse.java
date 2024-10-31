package com.example.coursefordevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseBestSaleResponse {
    private Long id;
    private String coverImage;
    private String title;
    private Long numberUserPayment;
    private String idIntructor;
    private String fullNameIntructor;
    private Long numberSection;
}
