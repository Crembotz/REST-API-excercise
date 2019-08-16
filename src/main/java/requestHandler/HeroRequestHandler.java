package requestHandler;

import BL.HeroBL;
import DAL.HeroDAL;
import entities.Hero;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import javax.xml.ws.http.HTTPException;

@RestController
public class HeroRequestHandler {

    private HeroBL heroBL;

    public HeroRequestHandler() {
        heroBL = new HeroBL(new HeroDAL("herodb"));
    }

    @RequestMapping("/hero")
    public Object getAllHeroes() {
        try {
            return heroBL.getAllHeroes();
        } catch (Exception e) {
            System.out.println("Exception was caused by:");
            e.printStackTrace();
        }
        return "No heroes were found.\nError code: " + new HTTPException(404).getStatusCode();
    }

    @RequestMapping("/hero/{id}")
    public Object getSpecificHeroById(@PathVariable int id) {
        try {
            return heroBL.getSpecificHero(id);
        } catch (SQLException e) {
            System.out.println("Exception caused by:");
            e.printStackTrace();
        }
        return "The hero with the id " + id + " hasn't been found.\nError code: " + new HTTPException(404).getStatusCode();
    }


    @RequestMapping("/saviours/{cid}")
    public Object getAllHeroesThatSavedCivilian(@PathVariable int cid) {
        try {
            return heroBL.getAllHeroesThatSavedCivilian(cid);
        } catch (SQLException e) {
            System.out.println("Exception caused by:");
            e.printStackTrace();
        }
        return "This civilian wasn't saved by any heroes thus far.\nError code: " + new HTTPException(404).getStatusCode();
    }

    @PostMapping("/hero/add")
    public Object addNewHero(@RequestBody Hero hero) {
        try {
            if (hero != null && heroBL.addNewHero(hero) != null)
                return hero;
        } catch (SQLException e) {
            System.out.println("Exception caused by:\n");
            e.printStackTrace();

        }
        return "Unable to add the } with the provided details, please make sure the information you provided was entered correctly.\nError code: " + new HTTPException(400).getStatusCode();

    }
}
