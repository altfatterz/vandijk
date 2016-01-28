package com.elerna;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class VandijkApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VandijkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running with " + args.length + " inputs");
        Set<String> isbns = new HashSet<>();


        for (String arg : args) {
            System.out.println("Parsing " + arg);
            Document doc = Jsoup.parse(new File(arg), StandardCharsets.UTF_8.name());

            Elements elements = doc.getElementsByAttributeValue("class", "tooltip-isbn");
            elements.forEach(element -> isbns.add(element.attr("title")));

            System.out.println("isbn size:" + isbns.size());
        }

        String result = isbns.stream().map(Objects::toString).collect(Collectors.joining(","));
        System.out.println(result);
        System.out.println("final isbn size:" + isbns.size());

    }
}
