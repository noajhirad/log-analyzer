package org.example;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import eu.bitwalker.useragentutils.UserAgent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogLine {

    private InetAddress ip;
    private UserAgent userAgent;
    private String browser;
    private String os;
    private String country;

    public LogLine(String line) {

        extractIP(line);
        extractUserAgent(line);
        extractBrowser();
        extractCountry();
        extractOS();
    }

    private void extractIP(String line) {

        String ipRegex = "^(?<ip>\\S+)";
        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String ipStr = matcher.group("ip");

            try {
                this.ip = InetAddress.getByName(ipStr);
            } catch (UnknownHostException e) {
                this.ip = null;
            }
        } else {
            this.ip = null;
        }
    }

    private void extractUserAgent(String line) {

        String regex =
                "(?<ip>\\S+)\\s" +                                  // IP Address
                        "-\\s-\\s" +                                // Skipping unused fields
                        "\\[(?<timestamp>[^\\]]+)\\]\\s" +          // Timestamp
                        "\"(?<method>\\S+)\\s(?<url>\\S+)\\s(?<protocol>\\S+)\"\\s" + // HTTP Method, URL, Protocol
                        "(?<statuscode>\\d+)\\s" +                 // Status code
                        "(?<responsesize>-|\\d+)\\s" +             // Response size, allowing '-'
                        "\"(?<referrer>[^\"]*)\"\\s" +              // Referrer URL, which might be empty
                        "\"(?<userAgent>[^\"]*)\"\\s?" +           // User Agent, which might be missing or empty
                        "(?:\\d+\\s\\d+\\s-\\s\\d+)?$";             // Optional extra fields if present

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String userAgentStr = matcher.group("userAgent");
            this.userAgent = UserAgent.parseUserAgentString(userAgentStr);
        } else {
            this.userAgent = null;
        }
    }

    private void extractCountry() {

        try {

            if (ip != null) {
                File database = new File("GeoLite2-Country.mmdb");
                DatabaseReader reader = new DatabaseReader.Builder(database).build();
                CountryResponse response = reader.country(this.ip);
                this.country = response.getCountry().getName();
            } else {
                this.country = null;
            }

        } catch (IOException | GeoIp2Exception e) {
            this.country = null;
        }
    }

    private void extractOS() {
        this.os = userAgent != null ? userAgent.getOperatingSystem().getGroup().getName() : null;
    }

    private void extractBrowser() {
        this.browser = userAgent != null ? userAgent.getBrowser().getGroup().getName() : null;
    }

    public InetAddress getIp() {
        return ip;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOS() {
        return os;
    }

    public String getCountry() {
        return country;
    }
}
