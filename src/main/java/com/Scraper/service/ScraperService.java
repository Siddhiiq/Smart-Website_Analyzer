package com.Scraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.Scraper.extractor.DataExtractor;
import com.Scraper.model.WebsiteData;

@Service
public class ScraperService {

    public WebsiteData scrape(String url) {
        WebsiteData data = new WebsiteData();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .timeout(10000)
                    .get();

            data.companyName = DataExtractor.extractCompanyName(doc);
            data.description = DataExtractor.extractDescription(doc);
            data.emails = DataExtractor.extractEmails(doc);
            data.phones = DataExtractor.extractPhones(doc);
            data.socialLinks = DataExtractor.extractSocialLinks(doc);
            data.logo = DataExtractor.extractLogo(doc);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return data;
    }
}