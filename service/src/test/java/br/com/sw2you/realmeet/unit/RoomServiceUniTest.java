package br.com.sw2you.realmeet.unit;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.utils.MapperUtils;
import br.com.sw2you.realmeet.utils.TestConstants;
import br.com.sw2you.realmeet.utils.TestDataCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

class RoomServiceUniTest extends BaseUnitTest {

    private RoomService victim;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setupEach() {
        victim = new RoomService(roomRepository, MapperUtils.roomMapper());
    }

    @Test
    void testGetRoomSucess() {
        var room = TestDataCreator.newRoomBuilder().id(TestConstants.DEFAULT_ROOM_ID).build();
        Mockito.when(roomRepository.findByIdAndActive(TestConstants.DEFAULT_ROOM_ID, true)).thenReturn(Optional.of(room));

        var dto = victim.getRoom(TestConstants.DEFAULT_ROOM_ID);
        Assertions.assertEquals(room.getId(), dto.getId());
        Assertions.assertEquals(room.getName(), dto.getName());
        Assertions.assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRoomSucessNotFound() {
        Mockito.when(roomRepository.findByIdAndActive(TestConstants.DEFAULT_ROOM_ID, true)).thenReturn(Optional.empty());
        Assertions.assertThrows(RoomNotFoundException.class, () -> victim.getRoom(TestConstants.DEFAULT_ROOM_ID));
    }
}
