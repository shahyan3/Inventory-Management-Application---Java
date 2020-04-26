package com.company;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static int EXIT = 0;


    public static void main(String[] args) throws InterruptedException {
        int input;
        while(true) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object

            // main menu - welcome
            mainMenu();
            input = myObj.nextInt();  // Read user input

            if(input == EXIT) {
                System.out.println("Exiting application...");
                TimeUnit.SECONDS.sleep(3);
                System.exit(0);
            }

            switch(input) {
                case 1:
                    staffMenu();
                    input = myObj.nextInt();  // Read user input
                    if(input == EXIT) break;

                case 2:
                    memberMenu();
                    input = myObj.nextInt();  // Read user input
                    if(input == EXIT) break;
            }

        }
    }

    public static void mainMenu() {
        System.out.println(" ");
        System.out.println("Welcome to the Community Library");
        System.out.println("========== Main Menu =============");
        System.out.println("1. Staff Login");
        System.out.println("2. Member Login");
        System.out.println("0. Exit");
        System.out.println("=================================");

        System.out.print("Please make selection (1-2, or 0 to exit): ");
    }

    public static void staffMenu() {
        System.out.println(" ");
        System.out.println("========== Staff Menu =============");
        System.out.println("1. Add a new movie DVD");
        System.out.println("2. Remove a movie DVD");
        System.out.println("3. Register a new Member");
        System.out.println("4. Find a registered member's phone number");
        System.out.println("0. Return to main menu");
        System.out.println("=================================");

        System.out.print("Please make selection (1-4, or 0 to return to main menu): ");
    }

    public static void memberMenu() {
        System.out.println(" ");
        System.out.println("========== Member Menu =============");
        System.out.println("1. Display all movies");
        System.out.println("2. Borrow a movie DVD");
        System.out.println("3. Return a movie DVD");
        System.out.println("4. List current borrowed movie DVDs");
        System.out.println("5. Display top 10 most popular movies");
        System.out.println("0. Return to main menu");
        System.out.println("=================================");

        System.out.print("Please make selection (1-5, or 0 to return to main menu): ");
    }
}
