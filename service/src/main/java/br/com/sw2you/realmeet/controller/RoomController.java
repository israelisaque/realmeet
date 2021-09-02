package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.RoomsApi;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController implements RoomsApi {

    private final Executor controllersExecutor;
    private final RoomService roomService;

    public RoomController(RoomService roomService, Executor controllersExecutor) {
        this.roomService = roomService;
        this.controllersExecutor = controllersExecutor;
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(Long id) {
        return supplyAsync(() -> roomService.getRoom(id), controllersExecutor).thenApply(r -> ResponseEntityUtils.ok(r));
    }
}
