package com.example.demo.roomreservation.roomreservationservice;

import com.example.demo.emailsender.MailgunJavaMailSender;
import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationInfoDto;
import com.example.demo.reservation.service.ReservationListDto;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.room.service.*;
import com.example.demo.roomreservation.guestrepository.GuestStatus;
import com.example.demo.roomreservation.guestservice.CreateFullGuestDto;
import com.example.demo.roomreservation.guestservice.FullGuestDto;
import com.example.demo.roomreservation.guestservice.GuestService;
import com.example.demo.user.service.UserDomainObject;
import com.example.demo.user.service.UserInfoDto;
import com.example.demo.user.service.UserService;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.reservation.service.ReservationDomainObject.getDuration;


@Service
public class RoomReservationService {
    final ReservationService reservationService;
    final UserService userService;
    final RoomService roomService;

    final MailgunJavaMailSender emailSender;
    final GuestService guestService;

    public RoomReservationService(ReservationService reservationService, UserService userService, RoomService roomService, MailgunJavaMailSender emailSender, GuestService guestService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.roomService = roomService;
        this.emailSender = emailSender;
        this.guestService=guestService;
    }

    private int calculateTotalSpent(List<ReservationDomainObject> reservations, Map<Long, RoomDomainObject> localRoomCache) {
        return reservations.stream()
                .mapToInt(reservation -> {
                    RoomDomainObject room = localRoomCache.computeIfAbsent(reservation.getRoomId(),
                            roomService::getById);
                    return room.getPrice() * getDuration(reservation);
                })
                .sum();
    }

    private Optional<AllUserInfoDto> buildAllUserInfoDto(UserDomainObject userDomainObject, Integer minTotalSpent, Map<Long, RoomDomainObject> localRoomCache) {
        Long userId = userDomainObject.getId();
        List<ReservationDomainObject> reservations = reservationService.getAllReservationsByUserId(userId);

        int totalSpent = calculateTotalSpent(reservations, localRoomCache);

        if (minTotalSpent == null || totalSpent >= minTotalSpent) {
            TimeFrameDto timeFrameDto = ReservationDomainObject.buildTimeFrameDto(reservations);
            List<ReservationListDto> reservationList = buildReservationListDto(reservations, localRoomCache);

            return Optional.of(AllUserInfoDto.builder()
                    .user_id(userId)
                    .user_email(userDomainObject.getEmail())
                    .totalSpent(totalSpent)
                    .timeFrameDto(timeFrameDto)
                    .reservationListDtos(reservationList)
                    .build());
        } else {
            return Optional.empty();
        }
    }

    public List<AllUserInfoDto> buildAllUserInfoDtoList(Integer limit, Integer minTotalSpent) {
        List<UserDomainObject> allUsers = userService.getAllUsers();
        Map<Long, RoomDomainObject> localRoomCache = new HashMap<>();

        return allUsers.stream()
                .map(userDomainObject -> buildAllUserInfoDto(userDomainObject, minTotalSpent, localRoomCache))
                .flatMap(Optional::stream)
                .sorted(Comparator.comparingInt(AllUserInfoDto::getTotalSpent).reversed())
                .limit(Optional.ofNullable(limit).orElse(allUsers.size()))
                .collect(Collectors.toList());

    }

    public List<ReservationListDto> buildReservationListDto(List<ReservationDomainObject> reservations, Map<Long, RoomDomainObject> localRoomCache) {
        return reservations.stream()
                .map(reservation -> {
                    Long roomId = reservation.getRoomId();

                    RoomDomainObject roomDomainObject = localRoomCache.computeIfAbsent(roomId, roomService::getById);

                    int totalAmountOfRentedTime = ReservationService.getTotalAmountOfRentedTime(roomId, reservations);

                    RoomInfoDto roomInfoDto = RoomInfoDto.builder()
                            .roomId(roomId)
                            .roomName(roomDomainObject.getName())
                            .build();
                    return ReservationListDto.builder()
                            .id(reservation.getId())
                            .startDate(reservation.getStartDate())
                            .endDate(reservation.getEndDate())
                            .roomInfoDto(roomInfoDto)
                            .generatedRevenue(totalAmountOfRentedTime * roomDomainObject.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
    }


    public List<ReservationInfoDto> buildReservationInfoDtoList(List<ReservationDomainObject> reservations) {
        Map<Integer, UserInfoDto> localUserCache = new HashMap<>();

        return reservations.stream()
                .map(reservation -> {
                    Integer userId = reservation.getUserId();

                    UserInfoDto userInfo = localUserCache.computeIfAbsent(userId, userService::getUserInfo);

                    return ReservationInfoDto.builder()
                            .reservationId(reservation.getId())
                            .userInfo(userInfo)
                            .stardDate(reservation.getStartDate())
                            .enddDate(reservation.getEndDate())
                            .build();
                })
                .collect(Collectors.toList());
    }


    public AllRoomInformationDto buildAllRoomInformationDto(Long roomId) {
        List<ReservationDomainObject> reservations = reservationService.getAllReservationsByRoomId(roomId);

        int totalAmountOfRentedTime = ReservationService.getTotalAmountOfRentedTime(roomId, reservations);
        TimeFrameDto timeFrameDto = ReservationDomainObject.buildTimeFrameDto(reservations);

        RoomDomainObject roomDomainObject = roomService.getById(roomId);

        return AllRoomInformationDto.builder()
                .name(roomDomainObject.getName())
                .generatedRevenue(totalAmountOfRentedTime * roomDomainObject.getPrice())
                .timeframe(timeFrameDto)
                .reservationInfoList(buildReservationInfoDtoList(reservations))
                .build();
    }

    public List<TopRented> getTopRentedRooms(Integer top, Integer minGeneratedRevenue) {
        return reservationService.getMostRented()
                .stream()
                .map(room -> TopRented.builder()
                        .roomId(room.getRoomId())
                        .nrOfReservations(room.getNrOfReservations())
                        .totalAmountOfRentedTime(room.getTotalAmountOfRentedTime())
                        .generatedRevenue(roomService.getById(room.getRoomId()).getPrice() * room.getTotalAmountOfRentedTime())
                        .build())
                .filter(topRented -> (minGeneratedRevenue == null || topRented.getGeneratedRevenue() > minGeneratedRevenue))
                .sorted((o1, o2) -> Double.compare(o2.getGeneratedRevenue(), o1.getGeneratedRevenue()))
                .limit(Optional.ofNullable(top).orElse(5))
                .collect(Collectors.toList());
    }

    public List<InviteStatus> sendInvitations(Long reservationId, List<Guest> guests) {
        ReservationDomainObject reservation = reservationService.findById(reservationId);
        RoomDomainObject room = roomService.getById(reservation.getRoomId());
        UserDomainObject user = userService.getById(reservation.getUserId());

        List<FullGuestDto> fullGuestDtos = guests.stream()
                .map(guest -> FullGuestDto.builder()
                        .reservationId(reservationId)
                        .name(guest.getName())
                        .email(guest.getEmail())
                        .status(GuestStatus.PENDING)
                        .build())
                .collect(Collectors.toList());

        guestService.saveAll(fullGuestDtos);
        List<CreateFullGuestDto> createFullGuestDto=guestService.findByReservationId(reservationId);
        return createFullGuestDto.stream()
                .map(fullGuestDto -> {
                    try {
                        sendEmail(fullGuestDto, user, room, reservation);
                        return new InviteStatus(fullGuestDto.getEmail(), "SENT", "200");
                    } catch (Exception e) {
                        return new InviteStatus(fullGuestDto.getEmail(), "ERRORED", e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    private void sendEmail(CreateFullGuestDto guest, UserDomainObject user, RoomDomainObject room, ReservationDomainObject reservation) {
        String encodedGuestId = Base64.getEncoder().encodeToString(guest.getId().toString().getBytes());
        String guestLinkRejected = "http://localhost:8080/api/reservation/status/" + encodedGuestId + "?status=REJECTED";
        String guestLinkAccepted = "http://localhost:8080/api/reservation/status/" + encodedGuestId + "?status=ACCEPTED";

        String subject = user.getName() + " has sent you an invite";
        String body = "<p>Hi " + guest.getName() + ",</p>" +
                "<p>You are invited to a room that is located in:<br>city: " + room.getCity() + "<br>street: " + room.getStreet() + "<br>street number: " + room.getStreetNo() + "<br>from " +
                reservation.getStartDate() + " to " + reservation.getEndDate() + ".</p>" +
                "<p><a href=\"" + guestLinkAccepted + "\" style=\"padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none;\">Accept</a>&nbsp;" +
                "<a href=\"" + guestLinkRejected + "\" style=\"padding: 10px 20px; background-color: #f44336; color: white; text-decoration: none;\">Reject</a></p>";

        Message message = Message.builder()
                .from(user.getEmail())
                .to(guest.getEmail())
                .subject(subject)
                .html(body)
                .build();

        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config("c4802ebd871393ebe6575f0a31523620-181449aa-5212a2d7")
                .createApi(MailgunMessagesApi.class);

        MessageResponse response = mailgunMessagesApi.sendMessage("sandboxe65fdf3087464f3ca217c1783cadc4be.mailgun.org", message);


    }



    public void updateStatus(@NonNull final Long guestId, @NonNull final GuestStatus status){
        guestService.updateStatus(guestId, status);
    }
    public FullGuestDto findById(@NonNull final Long guestId) {
        return guestService.findById(guestId);
    }

}
