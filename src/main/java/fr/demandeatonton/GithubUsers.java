package fr.demandeatonton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.demandeatonton.data.GithubUser;
import fr.demandeatonton.ui.UserCard;
import io.reactivex.Observable;

public class GithubUsers extends JFrame {
   JPanel panel = new JPanel();
   List<UserCard> userCards = new ArrayList<>();

   public GithubUsers() {
      super("Github Users");
      setPreferredSize(new Dimension(400, 200));
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      addUi();
      fetchGithubUsers();
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
   }

   private void addUi() {
      panel = new JPanel();
      JButton button = new JButton("Rafraichir");
      button.addActionListener(event -> this.fetchGithubUsers());
      add(new JLabel("3 utilisateurs de github au hasard"), BorderLayout.NORTH);
      add(panel, BorderLayout.CENTER);
      add(button, BorderLayout.SOUTH);
   }

   private void updateCards(String json) {
      ObjectMapper mapper = new ObjectMapper();
      // delete existing cards
      panel.invalidate();
      userCards.forEach(card -> panel.remove(card));
      userCards.clear();
      // JSON from file to Object
      try {
         List<GithubUser> obj = mapper.readValue(json, new TypeReference<List<GithubUser>>() {
         });
         obj.stream().unordered().limit(3).forEach(user -> addCard(user));
      } catch (IOException e) {
         e.printStackTrace();
      }
      panel.validate();

   }

   private void addCard(GithubUser user) {
      UserCard userCard = new UserCard(user.getLogin(), user.getAvatarUrl());
      userCards.add(userCard);
      panel.add(userCard);
   }

   private void fetchGithubUsers() {
      String url = "https://api.github.com/users?since=" + ((int) (Math.random() * 500.0));
      Observable<String> requestStream = Observable.just(url);
      System.out.println("Fetching " + url);

      requestStream.subscribe(theUrl -> {
         String json = readUrl(theUrl);
         Observable.just(json)
               .subscribe(jsonResult -> updateCards(jsonResult));
      }, throwable -> {
         JOptionPane.showMessageDialog(null, throwable.getMessage());
         System.out.println("Error: " + throwable.getMessage());
      });

   }

   String readUrl(String url) throws IOException {
      URL feedUrl = new URL(url);
      BufferedReader in = new BufferedReader(new InputStreamReader(feedUrl.openStream()));

      String inputLine;
      String res = "";
      while ((inputLine = in.readLine()) != null)
         res += inputLine;
      in.close();

      return res;
   }

   public static void main(String[] args) {
      new GithubUsers();
   }

}