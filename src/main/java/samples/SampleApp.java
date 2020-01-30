package samples;


import io.github.drxaos.browser.DynamicResourceHandler;
import io.github.drxaos.browser.FxBrowser;
import io.github.drxaos.browser.WebAppURLStreamHandlerFactory;
import javafx.application.Platform;
import javafx.concurrent.Worker;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.net.URL;

public class SampleApp {
    static FxBrowser fxBrowser;
    static JLabel statusbar;
    static JTextField urlField;

    static String colorToRgb(SystemColor color) {
        return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
    }

    static String APP_CSS = "body {\n" +
            "background-color: " + colorToRgb(SystemColor.window) + ";\n" +
            "}";

    public static void main(String... args) {
        DynamicResourceHandler dynamicResourceHandler = new DynamicResourceHandler();
        dynamicResourceHandler.addResource("/app.css", (url) -> new ByteArrayInputStream(APP_CSS.getBytes()));
        URL.setURLStreamHandlerFactory(new WebAppURLStreamHandlerFactory(SampleApp.class, dynamicResourceHandler));

        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();


            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            {
                fxBrowser = new FxBrowser("webapp:///index.html");
                panel.add(fxBrowser, BorderLayout.CENTER);
                fxBrowser.addOnLoaderStateChanged((observable, oldValue, newValue) -> {
                    statusbar.setText(newValue.name());
                    if (newValue == Worker.State.SUCCEEDED) {
                        frame.setTitle(fxBrowser.getTitle());
                    }
                });
                fxBrowser.addOnRelocate(url -> urlField.setText(url));
            }
            {
                statusbar = new JLabel("Loading...");
                panel.add(statusbar, BorderLayout.SOUTH);
            }
            {
                JPanel toolbar = new JPanel();
                toolbar.setLayout(new BorderLayout());
                panel.add(toolbar, BorderLayout.NORTH);
                {
                    JPanel buttons = new JPanel();
                    buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    toolbar.add(buttons, BorderLayout.WEST);

                    JButton back = new JButton("<<");
                    buttons.add(back);
                    back.addActionListener(e -> Platform.runLater(() -> fxBrowser.getWebHistory().go(-1)));
                    JButton forward = new JButton(">>");
                    buttons.add(forward);
                    forward.addActionListener(e -> Platform.runLater(() -> fxBrowser.getWebHistory().go(1)));
                    JButton reload = new JButton("Reload");
                    buttons.add(reload);
                    reload.addActionListener(e -> Platform.runLater(() -> fxBrowser.reload()));
                    urlField = new JTextField("about:blank");
                    toolbar.add(urlField, BorderLayout.CENTER);
                    urlField.addActionListener(e -> Platform.runLater(() -> fxBrowser.load(urlField.getText())));
                }
            }

            frame.getContentPane().add(panel);

            frame.setMinimumSize(new Dimension(640, 480));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
