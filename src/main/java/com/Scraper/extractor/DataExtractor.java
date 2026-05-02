package com.Scraper.extractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DataExtractor {

    public static String extractCompanyName(Document doc) {
        String og = doc.select("meta[property=og:site_name]").attr("content");
        if (!og.isEmpty()) return og;

        String title = doc.title();
        if (title.contains("|")) return title.split("\\|")[0];
        if (title.contains("-")) return title.split("-")[0];

        return title;
    }

    public static String extractDescription(Document doc) {
        String desc = doc.select("meta[name=description]").attr("content");
        return desc.isEmpty() ? "Not Found" : desc;
    }

    public static List<String> extractEmails(Document doc) {
        Set<String> emails = new HashSet<>();
        String text = doc.text();

        Matcher m = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+").matcher(text);
        while (m.find()) {
            emails.add(m.group());
        }

        return limit(emails);
    }

    public static List<String> extractPhones(Document doc) {
        Set<String> phones = new HashSet<>();
        String text = doc.text();

        Matcher m = Pattern.compile("\\+?\\d[\\d\\-\\s]{8,}\\d").matcher(text);
        while (m.find()) {
            phones.add(m.group());
        }

        return limit(phones);
    }

    public static List<String> extractSocialLinks(Document doc) {
        Set<String> links = new HashSet<>();

        for (Element a : doc.select("a[href]")) {
            String href = a.attr("abs:href");

            if (href.contains("linkedin.com") ||
                href.contains("twitter.com") ||
                href.contains("facebook.com") ||
                href.contains("instagram.com")) {

                links.add(href);
            }
        }

        return limit(links);
    }

    public static String extractLogo(Document doc) {
        Element logo = doc.selectFirst("img[src*=logo]");
        return logo != null ? logo.attr("abs:src") : "Not Found";
    }

    private static List<String> limit(Set<String> set) {
        List<String> list = new ArrayList<>(set);
        return list.size() > 3 ? list.subList(0, 3) : list;
    }
}