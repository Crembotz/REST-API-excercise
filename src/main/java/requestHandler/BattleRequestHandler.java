package requestHandler;

import BL.BattleBL;
import DAL.BattleDAL;
import DAL.BattleHeroesDAL;
import DAL.SavioursDAL;
import entities.Battle;
import entities.Event;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.sql.SQLException;


@RestController
public class BattleRequestHandler {
    private BattleBL battleBL;

    public BattleRequestHandler() {
        String dbName = "herodb";
        battleBL = new BattleBL(new BattleDAL(dbName),new SavioursDAL(dbName),new BattleHeroesDAL(dbName));
    }

    @RequestMapping("/herobattles/{id}")
    public Object getHeroBattles(@PathVariable int id) {
        try {
            return battleBL.getBattlesByHeroId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "This hero hasn't participated in any battles yet, must be new on the job... or a coward!!:P\nError code: " + new HTTPException(404).getStatusCode();
    }

    @PostMapping("/battle/add")
    public String addNewBattle(@RequestBody Event event) {
        try {
            Battle battle = battleBL.addNewBattle(event.getvName(), event.getvPowerLevel(), event.getPointString(), event.getCid());
            if (battle != null) {
                if (battle.isWin())
                    return "The villain has been defeated.";
                return "The villain has won";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "New battle insertion has failed.\nError code: " + new HTTPException(400).getStatusCode();
    }

    @GetMapping("/battle/{location}")
    public Object getHeroesThatCanFightVillain(@PathVariable String location) {
        try {
            return battleBL.getHeroesThatCanFightVillain(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No heroes can arrive at the scene, we're doomed!!!\nError code: " + new HTTPException(404).getStatusCode();
    }


}
