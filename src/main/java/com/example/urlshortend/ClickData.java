package com.example.urlshortend;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClickData {
    private LocalDateTime timestamp;
    private String referrer;
    private String location; // dummy placeholder
}
