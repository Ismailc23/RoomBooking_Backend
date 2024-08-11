package com.rest.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long referenceId;

    @ManyToOne
    @JoinColumn(name = "customer_Id",nullable = false)
    @JsonIgnore
    private CustomerEntity customer;

    private String customerFirstName;

    private String customerLastName;

    private String roomType;

    private int roomPrice;

    @ManyToOne
    @JoinColumn(name = "room_Number",nullable = false)
    @JsonIgnore
    private RoomEntity room;

    private LocalDate bookedDate = LocalDate.now();

    private LocalDate stayStartDate;

    private LocalDate stayEndDate;
}
