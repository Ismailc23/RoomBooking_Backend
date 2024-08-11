package ServicesTest;

import com.rest.Entity.BookingEntity;
import com.rest.Entity.RoomEntity;
import com.rest.Repository.BookingRepository;
import com.rest.Repository.RoomRepository;
import com.rest.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RoomService.class)
@AutoConfigureMockMvc
public class RoomServiceTest {

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;

    private RoomEntity room;


    @BeforeEach
    void setUp() {
        room = new RoomEntity();
        room.setRoomNumber(100L);
        room.setType("3BHK");
        room.setAvailable(true);
        room.setPrice(1000);
    }

    @Test
    void testCreateRoom()
    {
        when(roomRepository.findById(100L)).thenReturn(Optional.empty());
        RoomEntity roomEntity = roomService.createRoom(room);
        assertEquals(room,roomEntity);

    }

    @Test
    void testCreateNoRoom()
    {
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        RoomEntity roomEntity = roomService.createRoom(room);
        assertEquals(null,roomEntity);
    }
    @Test
    void testGetRoomByIdSuccess()
    {
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        Optional<RoomEntity> opRoom = roomService.getRoomById(100L);
        assertNotNull(opRoom);
    }

    @Test
    void testGetRoomByIdFailure()
    {
        when(roomRepository.findById(100L)).thenReturn(null);
        Optional<RoomEntity> opRoom = roomService.getRoomById(100L);
        assertNull(opRoom);
    }

    @Test
    void testupdateRoomSuccess()
    {
        BookingEntity booking = new BookingEntity();
        booking.setReferenceId(1L);
        booking.setRoomType("3BHK");
        booking.setRoomPrice(1000);
        booking.setRoom(room);

        RoomEntity updatedRoom = new RoomEntity();
        updatedRoom.setRoomNumber(100L);
        updatedRoom.setPrice(1000);
        updatedRoom.setType("3BHK");

        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        when(bookingRepository.findByRoom_RoomNumber(100L)).thenReturn(Optional.of(booking));
        when(bookingRepository.getByRoom_RoomNumber(100L)).thenReturn(booking);
        RoomEntity updateRoom = roomService.updateRoom(updatedRoom);
        assertNotNull(updatedRoom);

    }

    @Test
    void testupdateRoomFailureRoomNotExist()
    {
        when(roomRepository.findById(100L)).thenReturn(Optional.empty());
        RoomEntity noUpdate = roomService.updateRoom(room);
        assertNull(noUpdate);
    }

    @Test
    void testupdateRoomFailureNoBooking()
    {
        when(roomRepository.findById(100L)).thenReturn(Optional.of(room));
        when(bookingRepository.findByRoom_RoomNumber(100L)).thenReturn(Optional.empty());
        RoomEntity noUpdate = roomService.updateRoom(room);
        assertNull(noUpdate);
    }

    @Test
    void testDeleteRoomSuccess()
    {
        when(roomRepository.existsById(100L)).thenReturn(true);
        boolean deleteRoom = roomService.deleteRoom(100L);
        assertTrue(deleteRoom);
    }

    @Test
    void testDeleteRoomFailure()
    {
        when(roomRepository.existsById(100L)).thenReturn(false);
        boolean deleteRoom = roomService.deleteRoom(100L);
        assertFalse(deleteRoom);
    }

}
