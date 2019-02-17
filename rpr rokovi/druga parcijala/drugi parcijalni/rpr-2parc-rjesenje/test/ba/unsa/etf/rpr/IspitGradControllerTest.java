package ba.unsa.etf.rpr;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGradControllerTest {
    Stage theStage;
    GradController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        ctrl = new GradController(null, dao.drzave());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }


    @Test
    public void testPoljeNadmorska(FxRobot robot) {
        TextField fld = robot.lookup("#fieldNadmorskaVisina").queryAs(TextField.class);
        assertNotNull(fld);
    }

    @Test
    public void testValidacijaNadmorskeVisine(FxRobot robot) {
        // Upisujemo nadmorsku visinu
        robot.clickOn("#fieldNadmorskaVisina");
        robot.write("-401");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Nadmorska visina nevalidna
        TextField fld = robot.lookup("#fieldNadmorskaVisina").queryAs(TextField.class);
        Background bg = fld.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
    }

    @Test
    public void testValidacijaNadmorskeVisine2(FxRobot robot) {
        // Upisujemo nadmorsku visinu
        robot.clickOn("#fieldNadmorskaVisina");
        robot.write("8001");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Nadmorska visina i dalje nevalidna
        TextField fld = robot.lookup("#fieldNadmorskaVisina").queryAs(TextField.class);
        Background bg = fld.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
    }

    @Test
    public void testValidacijaNadmorskeVisine3(FxRobot robot) {
        robot.clickOn("#fieldNadmorskaVisina");
        robot.write("-399");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Nadmorska visina sada validna
        TextField fld = robot.lookup("#fieldNadmorskaVisina").queryAs(TextField.class);
        Background bg = fld.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("adff2f"))
                colorFound = true;
        assertTrue(colorFound);
    }

    @Test
    public void testVracaNadmorsku(FxRobot robot) {
        // Upisujemo grad
        robot.clickOn("#fieldNaziv");
        robot.write("Sarajevo");
        robot.clickOn("#fieldBrojStanovnika");
        robot.write("350000");

        robot.clickOn("#fieldNadmorskaVisina");
        robot.write("7999");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Grad grad = ctrl.getGrad();
        assertEquals(7999, grad.getNadmorskaVisina());
    }
}

