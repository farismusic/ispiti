package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGlavnaTest {
    Stage theStage;
    GlavnaController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        ctrl = new GlavnaController();
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
    public void testDodajGradNadmorska(FxRobot robot) {
        ctrl.resetujBazu();

        // Otvaranje forme za dodavanje
        robot.clickOn("#btnDodajGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#fieldNaziv");
        robot.write("Sarajevo");

        robot.clickOn("#fieldBrojStanovnika");
        robot.write("350000");

        robot.clickOn("#fieldNadmorskaVisina");
        robot.write("550");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je Sarajevo dodano u bazu?
        GeografijaDAO dao = GeografijaDAO.getInstance();
        assertEquals(6, dao.gradovi().size());

        boolean pronadjeno = false;
        for(Grad grad : dao.gradovi())
            if (grad.getNaziv().equals("Sarajevo") && grad.getNadmorskaVisina() == 550)
                pronadjeno = true;
        assertTrue(pronadjeno);
    }

    @Test
    public void testIzmijeniGradNadmorska(FxRobot robot) {
        ctrl.resetujBazu();

        // 250 ne smije biti default nadmorska visina za Graz jer je to "varanje"
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad graz = dao.nadjiGrad("Graz");
        assertNotEquals(250, graz.getNadmorskaVisina());

        // Mijenjamo grad Graz
        robot.clickOn("Graz");
        robot.clickOn("#btnIzmijeniGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        robot.clickOn("#fieldNadmorskaVisina");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("250");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je promijenjen broj stanovnika Graza?
        graz = dao.nadjiGrad("Graz");
        assertEquals(250, graz.getNadmorskaVisina());
    }

    @Test
    public void testKolonaNadmorska(FxRobot robot) {
        // Postavljamo nadmorsku visinu za Graz da možemo testirati tabelu
        ctrl.resetujBazu();

        robot.clickOn("Graz");
        robot.clickOn("#btnIzmijeniGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        robot.clickOn("#fieldNadmorskaVisina");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("250");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        TableView<Grad> tableViewGradovi = robot.lookup("#tableViewGradovi").queryAs(TableView.class);
        assertNotNull(tableViewGradovi);

        boolean found = false;
        for (TableColumn column : tableViewGradovi.getColumns()) {
            if (column.getText().equals("Nadmorska visina")) {
                found = true;
            }
        }
        assertTrue(found);
    }
}
