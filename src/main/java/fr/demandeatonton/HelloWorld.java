package fr.demandeatonton;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class HelloWorld {
   public static void main(String[] args) throws InterruptedException {
      Observable.just("Hello world").subscribe(System.out::println);
      Observable.timer(2, TimeUnit.SECONDS).subscribe(seconds -> System.out.println("2 seconds passed"));

      Thread.sleep(3000);
   }
}