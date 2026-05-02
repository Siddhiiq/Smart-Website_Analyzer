package com.Scraper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Scraper.model.WebsiteData;
import com.Scraper.service.ScraperService;

@RestController
@RequestMapping("/analyze")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @PostMapping
    public WebsiteData analyze(@RequestParam String url) {
        return scraperService.scrape(url);
    }
}