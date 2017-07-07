package fr.demandeatonton.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserCard extends JPanel {
   String name;
   String imgUrl;
   JLabel lblImage;
   JLabel lblName;

   public UserCard(String name, String imgUrl) {
      this.name = name;
      this.imgUrl = imgUrl;

      lblImage = new JLabel();
      lblImage.setPreferredSize(new Dimension(100, 100));
      try {
         lblImage.setIcon(new ImageIcon(new URL(imgUrl)));
      } catch (MalformedURLException e) {
         lblImage.setText("Unable to download image!");
         e.printStackTrace();
      }
      lblName = new JLabel(name);
      setLayout(new BorderLayout());
      add(lblImage, BorderLayout.CENTER);
      add(lblName, BorderLayout.SOUTH);
   }

}
