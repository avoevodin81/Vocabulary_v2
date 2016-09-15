package vocabulary.manager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URLOpener {

    public static void openURL(String url) {
        url = "https://translate.google.ru/#en/ru/" + url.replaceAll(" ", "%20");
        if (!url.startsWith("http")) {
            JOptionPane.showMessageDialog(null, "The url address is " + url);
        } else {
            try {
                URI uri = new URI(url);
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Something went wrong! " + e.toString());
            }
        }

    }
}
