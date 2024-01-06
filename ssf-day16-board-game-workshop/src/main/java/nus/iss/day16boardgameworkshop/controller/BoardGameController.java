package nus.iss.day16boardgameworkshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import nus.iss.day16boardgameworkshop.model.BoardGame;
import nus.iss.day16boardgameworkshop.service.BoardGameService;
import nus.iss.day16boardgameworkshop.utility.BoardGameUtility;

@RestController
@RequestMapping(path = "/api")
public class BoardGameController {

    @Value("${user.id}")
    String redisKey;

    @Autowired
    private BoardGameService boardGameSvc;

    // Task 1
    @PostMapping(path = "/boardgame", consumes = "application/json")
    public ResponseEntity<String> postBoard(@RequestBody String boardJson) {
        // process String to JsonObject
        JsonArray boardJsonArray = BoardGameUtility.toJson(boardJson);

        // redisKey = userId
        System.out.println("redis key: " + redisKey);

        // Store data into redis
        boardGameSvc.addArrayOfBoardGames(redisKey, boardJsonArray);

        // Generate JSON reply
        JsonObject response = Json.createObjectBuilder()
                                .add("insert_count", 1)
                                .add("id", redisKey)
                                .build();
        System.out.println(response);

        // return 201 status code when inserted
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(201));
    }

    // Task 2
    @GetMapping(path = "/boardgame/{gid}", produces = "application/json")
    public ResponseEntity<String> getBoard(@PathVariable String gid) {
        
        Optional<BoardGame> optBoardGame = boardGameSvc.getBoardGame(redisKey, gid);
        if (!optBoardGame.isPresent()) {
            return new ResponseEntity<>(BoardGameUtility.getNotFoundResponse().toString(),HttpStatus.NOT_FOUND);
        } 
        BoardGame boardGame = optBoardGame.get();
        // Build response
        JsonObject response = BoardGameUtility.convertBoardGameToJsonObject(boardGame);

        return new ResponseEntity<String>(response.toString(), HttpStatus.FOUND);
    }

    // Task 3 - PUT request
    @PutMapping(path = "/boardgame/{gid}")
    public ResponseEntity<String> putBoard(@RequestBody String body, 
        @PathVariable String gid,
        @RequestParam(name="upsert", required=false, defaultValue = "false") String upsert) {

        // Update board game with key = gid
        JsonObject newBoardGameJsonObject = BoardGameUtility.toJson(body).getJsonObject(0);
        BoardGame newBoardGame= BoardGameUtility.convertJsonObjectToBoardGame(newBoardGameJsonObject);

        ResponseEntity<String> response = boardGameSvc.updateBoardGame(redisKey, gid, upsert, newBoardGame);

        return response;
    }
    
}
