package cn.kamikuz.kaiheiguimanager.utils.gson;

public @interface JsonSubtype {
    Class<?> clazz();

    String name();
}
