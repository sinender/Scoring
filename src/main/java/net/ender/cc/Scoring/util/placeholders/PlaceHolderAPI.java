package net.ender.cc.Scoring.util.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolderAPI extends PlaceholderExpansion {
    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "et";
    }

    @Override
    public String getAuthor() {
        return "Ender";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
