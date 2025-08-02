package com.example.urlshortend;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrl {
    private String originalUrl;
    private String shortCode;
    private LocalDateTime creationTime;
    private LocalDateTime expiryTime;
    private int clickCount;
    private List<ClickData> clicks = new ArrayList<>();
}
