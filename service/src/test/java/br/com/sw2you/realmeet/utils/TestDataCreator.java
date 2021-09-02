package br.com.sw2you.realmeet.utils;

import br.com.sw2you.realmeet.domain.entity.Room;

public final class TestDataCreator {

    private TestDataCreator() {}

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(TestConstants.DEFAULT_ROOM_NAME).seats(TestConstants.DEFAULT_ROOM_SEATS);
    }
}
