package ru.itis.examples;

import java.util.Scanner;

public class MainNullPointerException {
    public static void main(String[] args) {
        Scanner scanner = null;
        int a = scanner.nextInt();
    }
}
