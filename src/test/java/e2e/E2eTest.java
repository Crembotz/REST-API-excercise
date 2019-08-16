package e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Hero;
import org.junit.Test;
import org.postgresql.geometric.PGpolygon;
import types.PowerCategory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;

public class E2eTest {

    @Test
    public void e2eTest() {
        String targetURL = "http://localhost:8080/hero/";
        HttpURLConnection connection;
        URL url;
        try {
            String parameters = URLEncoder.encode("421", "UTF-8");
            targetURL += parameters;
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    Integer.toString(parameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(response.toString());
            Hero responseHero = mapper.treeToValue(obj, Hero.class);
            Hero hero = new Hero(421, "Koa", "Tiki", 1234, 1234,
                    new PGpolygon("((1,1),(6,6),(1,1))"), "Elemental powers", PowerCategory.Categories.spower);
            assertEquals(responseHero, hero);
        } catch (SQLException | IOException e) {
            fail("No such hero was found.");
        }
    }


}
