package com.example.demo.user.authentication;

import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.roomreservation.guestservice.FullGuestDto;
import com.example.demo.roomreservation.roomreservationservice.RoomReservationService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

public class ValidStatusUpdateFilter implements Filter {

    private final ReservationService reservationService;
    private final RoomReservationService roomReservationService;

    public ValidStatusUpdateFilter(ReservationService reservationService, RoomReservationService roomReservationService) {
        this.reservationService = reservationService;
        this.roomReservationService = roomReservationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String encodedGuestId = httpRequest.getRequestURI().substring(httpRequest.getRequestURI().lastIndexOf('/') + 1);
        String decodedGuestIdStr = new String(Base64.getDecoder().decode(encodedGuestId));
        Long guestId = Long.parseLong(decodedGuestIdStr);

        FullGuestDto fullGuestDto = roomReservationService.findById(guestId);
        ReservationDomainObject reservation = reservationService.findById(fullGuestDto.getReservationId());
        Timestamp reservationStartDate = reservation.getStartDate();

        if (reservationStartDate.toLocalDateTime().isBefore(LocalDateTime.now())) {
            httpResponse.sendError(HttpStatus.BAD_REQUEST.value(), "Cannot modify status for a reservation that has already started or/and has finished");
            return;
        }

        chain.doFilter(request, response);
    }
}
