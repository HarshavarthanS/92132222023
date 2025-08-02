package com.example.urlshortend;

@Service
public class UrlShortenerService {

    private final Map<String, ShortUrl> urlMap = new ConcurrentHashMap<>();

    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request, String host) {
        String shortcode = request.getShortcode() != null ? request.getShortcode() : generateShortCode();

        if (urlMap.containsKey(shortcode)) {
            throw new RuntimeException("Shortcode already exists.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusMinutes(
                request.getValidity() != null ? request.getValidity() : 30);

        ShortUrl shortUrl = new ShortUrl(
                request.getUrl(),
                shortcode,
                now,
                expiry,
                0,
                new ArrayList<>());

        urlMap.put(shortcode, shortUrl);

        return new CreateShortUrlResponse(host + "/" + shortcode, expiry.toString());
    }

    public String getOriginalUrl(String shortcode, String referrer) {
        ShortUrl shortUrl = urlMap.get(shortcode);
        if (shortUrl == null) throw new RuntimeException("Shortcode not found.");
        if (LocalDateTime.now().isAfter(shortUrl.getExpiryTime()))
            throw new RuntimeException("Link expired.");

        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrl.getClicks().add(new ClickData(LocalDateTime.now(), referrer, "IN")); // dummy loc

        return shortUrl.getOriginalUrl();
    }

    public ShortUrl getStatistics(String shortcode) {
        if (!urlMap.containsKey(shortcode)) throw new RuntimeException("Shortcode not found.");
        return urlMap.get(shortcode);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
