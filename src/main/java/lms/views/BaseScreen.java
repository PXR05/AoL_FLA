package lms.views;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import lms.Library;

public abstract class BaseScreen {
    protected final Library library = Library.getInstance();
    protected final ScreenManager screenManager = ScreenManager.getInstance();
    
    private static final double WINDOW_WIDTH_PERCENTAGE = 0.8;
    private static final double WINDOW_HEIGHT_PERCENTAGE = 0.8;

    public void show() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * WINDOW_WIDTH_PERCENTAGE;
        double height = screenBounds.getHeight() * WINDOW_HEIGHT_PERCENTAGE;
        
        Scene scene = new Scene(createContent(), width, height);
        screenManager.getPrimaryStage().setScene(scene);
        screenManager.getPrimaryStage().setTitle(getTitle());
        
        screenManager.getPrimaryStage().centerOnScreen();
        screenManager.getPrimaryStage().show();
    }

    protected abstract Region createContent();
    protected abstract String getTitle();
}