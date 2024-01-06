package nus.iss.day16boardgameworkshop.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.day16boardgameworkshop.model.BoardGame;

@Repository
public class BoardGameRepository {

    @Autowired @Qualifier("boardRedisTemplate")
    private RedisTemplate<String, String> template;

    public String serializeBoardGame(BoardGame boardGame) {
        String gid = boardGame.getGid().toString();
        String name = boardGame.getName();
        String year = boardGame.getYear().toString();
        String ranking = boardGame.getRanking().toString();
        String users_rated = boardGame.getUsers_rated().toString();
        String url = boardGame.getUrl();
        String image = boardGame.getImage();
        // Build String boardGame for redis input
        StringBuilder sb = new StringBuilder()
                            .append("gid:"+gid+",")
                            .append("name:"+name+",")
                            .append("year:"+year+",")
                            .append("ranking:"+ranking+",")
                            .append("users_rated:"+users_rated+",")
                            .append("url:"+url+",")
                            .append("image:"+image);
        String body = sb.toString();
        return body;
    }

    public void addBoardGame(String redisKey, BoardGame boardGame) {
        // rediskey -> {"gid1": ["name:test, year:2000,..."]}
        //          -> {"gid2": ["name:test2, year:1994,..."]}
        String body = serializeBoardGame(boardGame);
        String gid = boardGame.getGid().toString();

        template.opsForHash().put(redisKey, gid, body);
    }

    public boolean hasGid(String redisKey, String gid) {
        if(template.opsForHash().hasKey(redisKey, gid)) {
            return true;
        }
        return false;
    }

    public BoardGame getBoardGame(String redisKey, String gid) {
        String queryResult = (String) template.opsForHash().get(redisKey, gid);
        // System.out.println(queryResult);

        BoardGame boardGame = new BoardGame();

        String[] tokens = queryResult.split(",");
        for (String s : tokens) {

            String[] keyValue = s.split(":", 2);

            switch (keyValue[0]) {
                case "gid": boardGame.setGid(Integer.parseInt(keyValue[1])); break;
                case "name": boardGame.setName(keyValue[1]); break;
                case "year": boardGame.setYear(Integer.parseInt(keyValue[1])); break;
                case "ranking": boardGame.setRanking(Integer.parseInt(keyValue[1])); break;
                case "users_rated": boardGame.setUsers_rated(Integer.parseInt(keyValue[1])); break;
                case "url": boardGame.setUrl(keyValue[1]); break;
                case "image": boardGame.setImage(keyValue[1]); break;
            }
        }
        return boardGame;
    }

    public void updateBoardGame(String redisKey, String gid, BoardGame boardGame) {
        String body = serializeBoardGame(boardGame);
        template.opsForHash().put(redisKey, gid, body);
    }

}
