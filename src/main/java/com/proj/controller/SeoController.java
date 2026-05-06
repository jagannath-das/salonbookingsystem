package com.proj.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeoController {

	@GetMapping(value = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
	public String robots(
			@RequestHeader(value = "Host", required = false) String host,
			@RequestHeader(value = "X-Forwarded-Proto", required = false) String forwardedProto) {
		String baseUrl = resolveBaseUrl(host, forwardedProto);
		return "User-agent: *\n"
				+ "Allow: /\n"
				+ "Sitemap: " + baseUrl + "/sitemap.xml\n";
	}

	@GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
	public String sitemap(
			@RequestHeader(value = "Host", required = false) String host,
			@RequestHeader(value = "X-Forwarded-Proto", required = false) String forwardedProto) {
		String baseUrl = resolveBaseUrl(host, forwardedProto);
		String lastModified = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
		List<String> urls = List.of(
				baseUrl + "/",
				baseUrl + "/index.html",
				baseUrl + "/login.html",
				baseUrl + "/register.html");

		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

		for (String url : urls) {
			xml.append("<url>");
			xml.append("<loc>").append(url).append("</loc>");
			xml.append("<lastmod>").append(lastModified).append("</lastmod>");
			xml.append("</url>");
		}

		xml.append("</urlset>");
		return xml.toString();
	}

	private String resolveBaseUrl(String host, String forwardedProto) {
		String scheme = (forwardedProto == null || forwardedProto.isBlank()) ? "http" : forwardedProto.trim();
		if (host == null || host.isBlank()) {
			return scheme + "://localhost:8080";
		}
		return scheme + "://" + host.trim();
	}
}
