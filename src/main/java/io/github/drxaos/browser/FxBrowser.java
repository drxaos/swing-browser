package io.github.drxaos.browser;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.Document;

public class FxBrowser extends JFXPanel {

    private String url = "about:blank";
    private Stage stage;
    private WebView webView;
    private WebEngine webEngine;
    private WebHistory webHistory;

    public FxBrowser() {
        initComponents();
    }

    public FxBrowser(String url) {
        this.url = url;
        initComponents();
    }

    private void initComponents() {
        // FX thread
        PlatformImpl.startup(() -> {
            stage = new Stage();
            stage.setTitle("FxBrowser");
            stage.setResizable(true);

            webView = new WebView();
            webEngine = webView.getEngine();
            webHistory = webEngine.getHistory();
            webEngine.load(url);

            Region root = new Region() {
                {
                    getChildren().add(webView);
                }

                @Override
                protected void layoutChildren() {
                    layoutInArea(webView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
                }

            };
            Scene scene = new Scene(root, getWidth(), getHeight(), javafx.scene.paint.Color.web("#FFF"));
            stage.setScene(scene);

            FxBrowser.this.setScene(scene);
        });
    }

    public WebHistory getWebHistory() {
        return webHistory;
    }

    public WebEngine getWebEngine() {
        return webEngine;
    }

    public WebView getWebView() {
        return webView;
    }

    public void load(String s) {
        webEngine.load(s);
    }

    public void loadContent(String s) {
        webEngine.loadContent(s);
    }

    public void reload() {
        webEngine.reload();
    }

    public Object executeScript(String js) {
        if (webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED) {
            return webEngine.executeScript(js);
        }
        return null;
    }

    public void addOnLoaderStateChanged(final ChangeListener<Worker.State> listener) {
        Platform.runLater(() -> webEngine.getLoadWorker().stateProperty().addListener(listener));
    }

    public void removeOnLoaderStateChanged(final ChangeListener<Worker.State> listener) {
        Platform.runLater(() -> webEngine.getLoadWorker().stateProperty().removeListener(listener));
    }

    public static interface RelocateListener {
        void onRelocate(String url);
    }

    public void addOnRelocate(final RelocateListener listener) {
        Platform.runLater(() -> webHistory.getEntries().addListener((ListChangeListener<WebHistory.Entry>) c -> {
            c.next();
            ObservableList<? extends WebHistory.Entry> list = c.getList();
            listener.onRelocate(list.get(list.size() - 1).getUrl());
        }));
    }

    public String getTitle() {
        return webEngine.getTitle();
    }

    public Document getDocument() {
        return webEngine.getDocument();
    }

    public void setOnAlert(EventHandler<WebEvent<String>> eventHandler) {
        webEngine.setOnAlert(eventHandler);
    }

    public void setOnJsStatusChanged(EventHandler<WebEvent<String>> eventHandler) {
        webEngine.setOnStatusChanged(eventHandler);
    }

    public void setOnResized(EventHandler<WebEvent<Rectangle2D>> eventHandler) {
        webEngine.setOnResized(eventHandler);
    }

    public void setOnVisibilityChanged(EventHandler<WebEvent<Boolean>> eventHandler) {
        webEngine.setOnVisibilityChanged(eventHandler);
    }

    public void setOnContextMenuRequested(EventHandler<? super ContextMenuEvent> eventHandler) {
        webView.setOnContextMenuRequested(eventHandler);
    }

    public void setOnMouseClicked(EventHandler<? super MouseEvent> eventHandler) {
        webView.setOnMouseClicked(eventHandler);
    }

    public void setOnScroll(EventHandler<? super ScrollEvent> eventHandler) {
        webView.setOnScroll(eventHandler);
    }

    public void setOnScrollFinished(EventHandler<? super ScrollEvent> eventHandler) {
        webView.setOnScrollFinished(eventHandler);
    }

    public void setOnKeyPressed(EventHandler<? super KeyEvent> eventHandler) {
        webView.setOnKeyPressed(eventHandler);
    }

    public void setOnKeyReleased(EventHandler<? super KeyEvent> eventHandler) {
        webView.setOnKeyReleased(eventHandler);
    }

    public void setOnShown(EventHandler<WindowEvent> eventHandler) {
        stage.setOnShown(eventHandler);
    }

    public void setOnHidden(EventHandler<WindowEvent> eventHandler) {
        stage.setOnHidden(eventHandler);
    }

    public void setContextMenuEnabled(boolean b) {
        webView.setContextMenuEnabled(b);
    }
}