package samples;


import io.github.drxaos.browser.FxBrowser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SampleBrowser {
    static FxBrowser fxBrowser;
    static JLabel statusbar;
    static JTextField urlField;

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame();


                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                {
                    fxBrowser = new FxBrowser("http://ya.ru/");
                    panel.add(fxBrowser, BorderLayout.CENTER);
                    fxBrowser.addOnLoaderStateChanged(new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                            statusbar.setText(newValue.name());
                            if (newValue == Worker.State.SUCCEEDED) {
                                frame.setTitle(fxBrowser.getTitle());
                            }
                        }
                    });
                    fxBrowser.addOnRelocate(new FxBrowser.RelocateListener() {
                        @Override
                        public void onRelocate(String url) {
                            urlField.setText(url);
                        }
                    });
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
                        back.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        fxBrowser.getWebHistory().go(-1);
                                    }
                                });
                            }
                        });
                        JButton forward = new JButton(">>");
                        buttons.add(forward);
                        forward.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        fxBrowser.getWebHistory().go(1);
                                    }
                                });
                            }
                        });
                        JButton reload = new JButton("Reload");
                        buttons.add(reload);
                        reload.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        fxBrowser.reload();
                                    }
                                });
                            }
                        });
                        urlField = new JTextField("about:blank");
                        toolbar.add(urlField, BorderLayout.CENTER);
                        urlField.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        fxBrowser.load(urlField.getText());
                                    }
                                });
                            }
                        });
                    }
                }


                frame.getContentPane().add(panel);

                frame.setMinimumSize(new Dimension(640, 480));
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
