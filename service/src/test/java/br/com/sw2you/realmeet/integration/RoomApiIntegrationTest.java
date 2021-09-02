package br.com.sw2you.realmeet.integration;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.utils.TestConstants;
import br.com.sw2you.realmeet.utils.TestDataCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class RoomApiIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RoomApi api;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testGetRoomSucess() {
        var room = TestDataCreator.newRoomBuilder().build();
        roomRepository.saveAndFlush(room);

        Assertions.assertNotNull(room.getId());
        Assertions.assertTrue(room.getActive());

        var dto = api.getRoom(room.getId());
        Assertions.assertEquals(room.getId(), dto.getId());
        Assertions.assertEquals(room.getName(), dto.getName());
        Assertions.assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRoomInactive() {
        var room = TestDataCreator.newRoomBuilder().active(false).build();
        roomRepository.saveAndFlush(room);

        Assertions.assertFalse(room.getActive());
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> api.getRoom(room.getId()));
    }

    @Test
    void testGetRoomDoesNotExist() {
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> api.getRoom(TestConstants.DEFAULT_ROOM_ID));
    }
}
