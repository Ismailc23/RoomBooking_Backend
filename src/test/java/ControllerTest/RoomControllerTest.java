package ControllerTest;

import com.rest.Entity.RoomEntity;
import com.rest.contoller.RoomController;
import com.rest.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RoomController.class)
@AutoConfigureMockMvc
public class RoomControllerTest {

    @MockBean
    private RoomService roomService;

    @Autowired
    private RoomController roomController;

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
        when(roomService.createRoom(room)).thenReturn(room);
        ResponseEntity<?> response = roomController.createRoom(room);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(room,response.getBody());
    }

    @Test
    void testCreateRoomNotCreated() {

        when(roomService.createRoom(room)).thenReturn(null);
        ResponseEntity<?> response = roomController.createRoom(room);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Not able to create", response.getBody());
    }

    @Test
    void testGetRoomById()
    {
        when(roomService.getRoomById(100L)).thenReturn(Optional.of(room));
        ResponseEntity<?> response = roomController.getRoom(100L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(room,response.getBody());
    }

    @Test
    void testNotGetRoomById()
    {
        when(roomService.getRoomById(100L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = roomController.getRoom(100L);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void updateRoomSuccess()
    {
        when(roomService.updateRoom(room)).thenReturn(room);
        ResponseEntity<?> response = roomController.updateRoom(100L,room);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(room,response.getBody());
    }

    @Test
    void updateRoomFailed()
    {
        when(roomService.updateRoom(room)).thenReturn(null);
        ResponseEntity<?> response = roomController.updateRoom(100L,room);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
        assertEquals("Not able to update",response.getBody());
    }

    @Test
    void testDeleteRoom()
    {
        when(roomService.deleteRoom(100L)).thenReturn(true);
        ResponseEntity<?> response = roomController.deleteRoom(100L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void testDeleteRoomFail()
    {
        when(roomService.deleteRoom(100L)).thenReturn(false);
        ResponseEntity<?> response = roomController.deleteRoom(100L);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
}

