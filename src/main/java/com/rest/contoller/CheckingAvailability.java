package com.rest.contoller;

import com.rest.Entity.RoomEntity;
import com.rest.services.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CheckingAvailability {

    @Autowired
    private RoomService roomService;

    @GetMapping("/api/available-rooms")
    public List<RoomEntity> getAvailableRooms(
            @RequestParam("stayStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate stayStartDate,
            @RequestParam("stayEndDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate stayEndDate) {
        return roomService.findAvailableRooms(stayStartDate, stayEndDate);
    }
}
