package com.pet.utils;

import java.text.Normalizer;

public class UrlUtil {
    public static String generateSlug(String title) {
        // Loại bỏ dấu và chuyển đổi thành chữ thường
        String slug = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");
        slug = slug.replaceAll("\\s+", "-");

        return slug;
    }
}
