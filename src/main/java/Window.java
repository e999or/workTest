import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Window extends JFrame implements ActionListener {
    JButton buttonOk;
    JTextField url;
    JTextField url2;
    JTextField url3;
    JTextField lo;
    JTextField pa;
    JTextField cer;
    String urlTo;
    String fileName;
    String countRepeat;


    Window() throws IOException {

        this.setSize(300, 300);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setResizable(false);


        buttonOk = new JButton("OK");
        buttonOk.addActionListener(this);

        url = new JTextField("Введи url");
        url.setPreferredSize(new Dimension(250,40));

        url2 = new JTextField("Куда сохранить");
        url2.setPreferredSize(new Dimension(250,40));

        url3 = new JTextField("Сколько");
        url3.setPreferredSize(new Dimension(60,40));

        lo = new JTextField("Lo");
        lo.setPreferredSize(new Dimension(100,40));

        pa = new JTextField("Pa");
        pa.setPreferredSize(new Dimension(100,40));

        cer = new JTextField("");
        cer.setPreferredSize(new Dimension(100,40));

        this.add(url);
        this.add(url2);
        this.add(url3);
        this.add(lo);
        this.add(pa);
        this.add(cer);

        this.add(buttonOk);
        this.setVisible (true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonOk){
            urlTo = url.getText();
            fileName = url2.getText();
            countRepeat = url3.getText();

        }
        try {
          if(!(cer.getText().isEmpty())){
              ConnectionWhithCer connectionWhithCer = new ConnectionWhithCer();
              connectionWhithCer.username =lo.getText();
              connectionWhithCer.password = pa.getText();
              connectionWhithCer.cer = cer.getText();
              int count = Integer.parseInt(countRepeat);
              for (int i = 0; i < count; i++) {
                  String s = connectionWhithCer.connectionToUrl(urlTo);
                  new SaveFile(fileName, s);
              }
          }else {
              ConnectionTo connectionTo = new ConnectionTo();
              connectionTo.username =lo.getText();
              connectionTo.password = pa.getText();
              int count = Integer.parseInt(countRepeat);
              for (int i = 0; i < count; i++) {
                  String s = connectionTo.connectionToUrl(urlTo);
                  new SaveFile(fileName, s);
              }
          }
        } catch (IOException | URISyntaxException | KeyStoreException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException ex) {
            ex.printStackTrace();
        }
    }

}
