package com.ceos23.spring_boot.controller;

import com.ceos23.spring_boot.domain.Reservation;
import com.ceos23.spring_boot.dto.ReservationRequest;
import com.ceos23.spring_boot.service.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // 🎟 예매
    @PostMapping
    public Reservation reserve(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ReservationRequest request
    ) {
        Long userId = Long.parseLong(userDetails.getUsername());

        return reservationService.reserve(
                userId,
                request.screeningId(),
                request.seatId()
        );
    }

    // ❌ 취소
    @DeleteMapping("/{id}")
    public void cancel(
            @PathVariable @Positive(message = "예매 ID는 양수여야 합니다.") Long id
    ) {
        reservationService.cancel(id);
    }
}