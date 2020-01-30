package samples;


import io.github.drxaos.browser.DynamicResourceHandler;
import io.github.drxaos.browser.FxBrowser;
import io.github.drxaos.browser.WebAppURLStreamHandlerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Sample1 {

    public static void main(String... args) {
        URL.setURLStreamHandlerFactory(new WebAppURLStreamHandlerFactory(Sample1.class, new DynamicResourceHandler()));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame();
                frame.setBackground(Color.red);

                JPanel panel = new JPanel();
                panel.setBackground(Color.blue);
                panel.setLayout(new BorderLayout());

                FxBrowser webView = new FxBrowser("http://ya.ru/");
                panel.add(webView, BorderLayout.CENTER);

                JButton button = new JButton("Reload");
                panel.add(button, BorderLayout.SOUTH);


                frame.getContentPane().add(panel);

                frame.setMinimumSize(new Dimension(640, 480));
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
