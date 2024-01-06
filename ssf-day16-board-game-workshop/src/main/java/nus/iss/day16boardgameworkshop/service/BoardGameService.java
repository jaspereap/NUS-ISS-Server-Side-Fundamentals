package nus.iss.day16boardgameworkshop.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import nus.iss.day16boardgameworkshop.model.BoardGame;
import nus.iss.day16boardgameworkshop.repository.BoardGameRepository;
import nus.iss.day16boardgameworkshop.utility.BoardGameUtility;

@Service
public class BoardGameService {
    
    @Autowired
    private BoardGameRepository boardGameRepo;

    public void addArrayOfBoardGames(String redisKey, JsonArray boardGameArray) {

        for (JsonValue obj : boardGameArray) {
            JsonObject currBoardGame = (JsonObject) obj;

            // System.out.println("Current Board Game: " + currBoardGame);

            // Map to BoardGame model
            BoardGame boardGame = BoardGameUtility.convertJsonObjectToBoardGame(currBoardGame);

            // Add to database
            boardGameRepo.addBoardGame(redisKey, boardGame);

        }   
    }

    public Optional<BoardGame> getBoardGame(String redisKey, String gid) {
        BoardGame boardGame = null;
        if (boardGameRepo.hasGid(redisKey, gid)) {
            boardGame = boardGameRepo.getBoardGame(redisKey, gid);
            return Optional.of(boardGame);
        }
        return Optional.ofNullable(boardGame);
    }

    public ResponseEntity<String> updateBoardGame(String redisKey, String gid, String upsert, BoardGame boardGame) {

        if (boardGameRepo.hasGid(redisKey, gid) | "true".equals(upsert)) {
            boardGameRepo.updateBoardGame(redisKey, gid, boardGame);
            JsonObject response = Json.createObjectBuilder()
                .add("update_count", 1)
                .add("id", redisKey)
                .build();
            return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<>(BoardGameUtility.getNotFoundResponse().toString(), HttpStatusCode.valueOf(400));
    }
}
