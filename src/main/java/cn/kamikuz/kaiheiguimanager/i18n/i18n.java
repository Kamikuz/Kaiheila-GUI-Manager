package cn.kamikuz.kaiheiguimanager.i18n;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

public class i18n {
  public static final i18n Instance = new i18n();
  public static Locale lang = Locale.CHINESE;
  private Properties langFile;

  public boolean init(){
    langFile = new Properties();
    try {
      InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("lang/" + lang.getLanguage() + ".properties")), StandardCharsets.UTF_8);
      langFile.load(inputStreamReader);
      inputStreamReader.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean changeLang(Locale lang){
    i18n.lang = lang;
    return init();
  }

  public static String format(String key, Object... args){
    return String.format(Instance.langFile.getProperty(key), args);
  }
}
