package fr.demandeatonton;

import io.reactivex.Observable;

public class HelloWorld {
   public static void main(String[] args) {
      Observable.just("Hello world").subscribe(System.out::println);
   }
}