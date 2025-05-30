package vesper.abyssify.filter;

/*
*
*
* This code was derived from Log Begone by AzureDoom which is licensed under The GNU General Public License, Version 3
*
*
*/

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.ServiceLoader;

public class FilterUtils {
    public static final Filter FILTER = new Filter();
    public static final JsonObject FETCH;

    static {
        try {
            FETCH = getConfig();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean shouldFilter(String msg){
        JsonArray phrases = FETCH.has("") ? FETCH.getAsJsonObject().getAsJsonArray() : null;

        if (msg != null){
            if (phrases != null){
                for (var phraseElement : phrases){
                    String phrase = phraseElement.getAsString();
                    if (msg .contains(phrase)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static JsonObject getConfig() throws FileNotFoundException {
        File config = new File(PLATFORM.getConfigLocation() + "/toFilter.json");

        if (!config.exists()) {
            try {
                Files.copy(Objects.requireNonNull(FilterUtils.class.getResourceAsStream("/assets/abyssify/toFilter.json")), config.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileReader reader = new FileReader(config)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveConfig(JsonObject config) throws IOException {
        File configFile = new File(PLATFORM.getConfigLocation() + "/toFilter.json");

        try (FileWriter writer = new FileWriter(configFile)){
            new Gson().toJson(config, writer);
        }
    }

    public static final PlatformHelper PLATFORM = load(PlatformHelper.class);

    public static <T> T load(Class<T> tClass){
        return ServiceLoader.load(tClass)
                .findFirst()
                .orElseThrow(() -> new NullPointerException(""));
    }

    public static final class SystemPrintFilter extends PrintStream{

        public SystemPrintFilter(PrintStream out) {
            super(out);
        }

        @Override
        public void println(String x) {
            if (!shouldFilter(x)) {
                super.println(x);
            }
        }

        @Override
        public void print(String s) {
            if (!shouldFilter(s)) {
                super.print(s);
            }
        }
    }
}
