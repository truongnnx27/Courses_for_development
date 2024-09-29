package com.example.coursefordevelopment.mapstruct;

import com.example.coursefordevelopment.dto.PaymentStatusDto;
import com.example.coursefordevelopment.entity.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PaymentStatusMapper {

    PaymentStatusMapper INSTANCE = Mappers.getMapper(PaymentStatusMapper.class);

    PaymentStatusDto paymentStatusToPaymentStatusDto(PaymentStatus paymentStatus);

    PaymentStatus paymentStatusDtoToPaymentStatus(PaymentStatusDto paymentStatusDto);
}
