package com.tcs.paymentmicroservice.service;

import com.tcs.paymentmicroservice.dto.PaymentDto;
import com.tcs.paymentmicroservice.entity.Payment;
import com.tcs.paymentmicroservice.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<PaymentDto> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(this::convertToDto);
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentDto, payment);
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentDto convertToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        BeanUtils.copyProperties(payment, paymentDto);
        return paymentDto;
    }
}
