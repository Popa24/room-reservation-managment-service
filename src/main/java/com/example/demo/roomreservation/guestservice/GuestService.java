package com.example.demo.roomreservation.guestservice;

import com.example.demo.roomreservation.guestrepository.GuestRepository;
import com.example.demo.roomreservation.guestrepository.GuestStatus;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    @NonNull
    final GuestRepository guestRepository;

    public GuestService(@NonNull final GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public void saveAll(@NonNull final List<FullGuestDto> guestEntities) {
        guestRepository.saveAll(guestEntities);
    }

    public void updateStatus(@NonNull final Long guestId, @NonNull final GuestStatus status) {
        guestRepository.updateStatus(guestId, status);
    }
    public List<CreateFullGuestDto> findByReservationId(@NonNull final Long reservationId) {
        return guestRepository.findByReservationId(reservationId);
    }
    public FullGuestDto findById(@NonNull final Long guestId) {
        return guestRepository.findById(guestId);
    }

}
