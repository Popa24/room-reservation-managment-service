package com.example.demo.reservation.service;

import com.example.demo.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationDomainObject reservation1;
    private ReservationDomainObject reservation2;

    @BeforeEach
    public void setUp() {
        reservation1 = ReservationDomainObject.builder()
                .id(1L)
                .userId(1)
                .roomId(1L)
                .startDate(Timestamp.valueOf("2023-04-01 14:00:00"))
                .endDate(Timestamp.valueOf("2023-04-01 16:00:00"))
                .build();

        reservation2 = ReservationDomainObject.builder()
                .id(2L)
                .userId(2)
                .roomId(1L)
                .startDate(Timestamp.valueOf("2023-04-01 18:00:00"))
                .endDate(Timestamp.valueOf("2023-04-01 20:00:00"))
                .build();
    }

    @Test
    public void testRoomAvailable() {
        when(reservationRepository.getReservationsByRoomId(any())).thenReturn(Arrays.asList(reservation1, reservation2));

        Timestamp newReservationStart = Timestamp.valueOf("2023-04-01 16:30:00");
        Timestamp newReservationEnd = Timestamp.valueOf("2023-04-01 17:30:00");

        boolean isRoomAvailable = reservationService.isRoomAvailable(1L, newReservationStart, newReservationEnd);

        assertTrue(isRoomAvailable);
    }

    @Test
    public void testRoomNotAvailable() {
        when(reservationRepository.getReservationsByRoomId(any())).thenReturn(Arrays.asList(reservation1, reservation2));

        Timestamp newReservationStart = Timestamp.valueOf("2023-04-01 15:00:00");
        Timestamp newReservationEnd = Timestamp.valueOf("2023-04-01 17:00:00");

        boolean isRoomAvailable = reservationService.isRoomAvailable(1L, newReservationStart, newReservationEnd);

        assertFalse(isRoomAvailable);
    }
}
