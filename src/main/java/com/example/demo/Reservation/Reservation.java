package com.example.demo.Reservation;

import com.example.demo.Room.Room;
import com.example.demo.User.userDomain;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    private @Id @GeneratedValue Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    private userDomain userId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    private Room roomId;
    private Date startDate;
    private Date endDate;

    public Reservation() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(userDomain userId) {
        this.userId = userId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public userDomain getUserId() {
        return userId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Reservation(Long id, userDomain userId, Room roomId, Date startDate, Date endDate) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
