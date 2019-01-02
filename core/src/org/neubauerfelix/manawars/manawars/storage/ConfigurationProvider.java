package org.neubauerfelix.manawars.manawars.storage;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public abstract class ConfigurationProvider {

    private static final Map<Class<? extends ConfigurationProvider>, ConfigurationProvider> providers = new HashMap();

    static {
        providers.put(YamlConfiguration.class, new YamlConfiguration());
    }

    public static ConfigurationProvider getProvider(Class<? extends ConfigurationProvider> provider)
    {
        return providers.get(provider);
    }

    /*------------------------------------------------------------------------*/
    public abstract void save(Configuration config, FileHandle file) throws IOException;

    public abstract void save(Configuration config, Writer writer);

    public abstract Configuration load(String fileName, boolean internal) throws IOException;

    public abstract Configuration load(FileHandle file) throws IOException;

    public abstract Configuration load(FileHandle file, Configuration defaults) throws IOException;

    public abstract Configuration load(Reader reader);

    public abstract Configuration load(Reader reader, Configuration defaults);

    public abstract Configuration load(InputStream is);

    public abstract Configuration load(InputStream is, Configuration defaults);

    public abstract Configuration load(String string);

    public abstract Configuration load(String string, Configuration defaults);
}