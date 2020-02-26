package com.app;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.service.EarthquakeService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class EarthquakeHazardApplication implements CommandLineRunner {

    private final EarthquakeService earthquakeService;

    public static void main(String[] args) {
        SpringApplication.run(EarthquakeHazardApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Enter your coordinates here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter latitude:");
        double longitude = scanner.nextDouble();
        System.out.println("Enter longitude:");
        double latitude = scanner.nextDouble();
        System.out.println(earthquakeService.createResultMessage(longitude, latitude));
    }
}
