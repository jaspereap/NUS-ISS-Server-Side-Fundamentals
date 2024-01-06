package nus.iss.day16boardgameworkshop.utility;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.iss.day16boardgameworkshop.model.BoardGame;

public class BoardGameUtility {
    
    public static JsonArray toJson(String input) {
        JsonArray jarray = null;
        try (JsonReader jreader = Json.createReader(new StringReader(input))) {
            jarray = jreader.readArray();
        } catch (Exception e) {}
        return jarray;
    }

    public static BoardGame convertJsonObjectToBoardGame(JsonObject obj) {
        Integer gid = obj.getInt("gid");
        String name = obj.getString("name");
        Integer year = obj.getInt("year");
        Integer ranking = obj.getInt("ranking");
        Integer users_rated = obj.getInt("users_rated");
        String url = obj.getString("url");
        String image = obj.getString("image");
        
        BoardGame boardGame = new BoardGame(gid, name, year, ranking, users_rated, url, image);
        return boardGame;
    }

    public static JsonObject convertBoardGameToJsonObject(BoardGame boardGame) {
        JsonObject object = Json.createObjectBuilder()
            .add("gid",boardGame.getGid())
            .add("name",boardGame.getName())
            .add("year",boardGame.getYear())
            .add("ranking",boardGame.getRanking())
            .add("users_rated", boardGame.getUsers_rated())
            .add("url", boardGame.getUrl())
            .add("image", boardGame.getImage())
            .build();

        return object;
    }

    public static JsonObject getNotFoundResponse() {
        JsonObject response = Json.createObjectBuilder()
            .add("found", 0)
            .build();
        return response;
    }
}
