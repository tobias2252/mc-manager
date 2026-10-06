package dev.tobiasbieber.mcmanager.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

// manages language property files
public class LangManager {

    private static final Logger log = LoggerFactory.getLogger(LangManager.class);

    private static final String langProps = "lang.mltext";

    private Locale currentLang;
    private ResourceBundle bundle;

    // system locale is used by default
    public LangManager() {
        this.currentLang = Locale.getDefault();
        this.bundle = ResourceBundle.getBundle(langProps, Locale.ROOT); // TODO remove Locale.ROOT later
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public String get(String key, Object... args) {
        return String.format(bundle.getString(key), args);
    }

    // reloads resource bundle using new locale
    public void changeLang(Locale newLang) {
        log.info("Setting language: {}", newLang.toLanguageTag());
        currentLang = newLang;
        bundle = ResourceBundle.getBundle(langProps, newLang);
    }

    public Locale getCurrentLang() {
        return currentLang;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
