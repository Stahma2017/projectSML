package com.example.stas.sml.utils;

public class UrlHelper {
    public static String PHOTO_SIZE = "400x400";

    public static String getUrlToPhoto(String prefix, String suffix){
      return prefix + PHOTO_SIZE + suffix;
    }
}
