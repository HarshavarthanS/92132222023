package com.example.urlshortend;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;

    @PostMapping("/shorturls")
    public ResponseEntity<?> createShortUrl(@RequestBody CreateShortUrlRequest request, HttpServletRequest req) {
        String host = req.getRequestURL().toString().replace(req.getRequestURI(), "");
        return new ResponseEntity<>(service.createShortUrl(request, host), HttpStatus.CREATED);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortcode, HttpServletRequest request) {
        String longUrl = service.getOriginalUrl(shortcode, request.getHeader("referer"));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/shorturls/{shortcode}")
    public ResponseEntity<?> getStats(@PathVariable String shortcode) {
        return ResponseEntity.ok(service.getStatistics(shortcode));
    }
}
