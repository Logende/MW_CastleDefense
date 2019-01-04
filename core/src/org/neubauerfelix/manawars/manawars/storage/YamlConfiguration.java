package org.neubauerfelix.manawars.manawars.storage;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class YamlConfiguration extends ConfigurationProvider {

    private final ThreadLocal<Yaml> yaml = new ThreadLocal<Yaml>()
    {
        @Override
        protected Yaml initialValue()
        {
            Representer representer = new Representer()
            {
                {
                    representers.put( Configuration.class, new Represent() {
                        @Override
                        public Node representData(Object data)
                        {
                            return represent(((Configuration) data).getSelf());
                        }
                    } );
                }
            };

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            return new Yaml(new Constructor(), representer, options);
        }
    };

    @Override
    public void save(Configuration config, FileHandle file) {
        BufferedWriter writer = new BufferedWriter(file.writer(false, "UTF-8"));
        save(config, writer);
    }

    @Override
    public void save(Configuration config, Writer writer) {
        yaml.get().dump(config.getSelf(), writer);
    }

    @Override
    public Configuration load(String fileName, boolean internal) {
        return load( internal ? Gdx.files.internal(fileName) : Gdx.files.local(fileName) );
    }

    @Override
    public Configuration load(FileHandle file) {
        return load(file, null);
    }

    @Override
    public Configuration load(FileHandle file, Configuration defaults) {
        BufferedReader reader = new BufferedReader(file.reader("UTF-8"));
        return load(reader, defaults);
    }

    @Override
    public Configuration load(Reader reader)
    {
        return load(reader, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(Reader reader, Configuration defaults) {
        Map<String, Object> map = yaml.get().loadAs( reader, LinkedHashMap.class );
        if (map == null) {
            map = new LinkedHashMap();
        }
        return new Configuration(map, defaults);
    }

    @Override
    public Configuration load(InputStream is)
    {
        return load(is, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(InputStream is, Configuration defaults) {
        Map<String, Object> map = yaml.get().loadAs(is, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }
        return new Configuration(map, defaults);
    }

    @Override
    public Configuration load(String string)
    {
        return load(string, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(String string, Configuration defaults) {
        Map<String, Object> map = yaml.get().loadAs(string, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap();
        }
        return new Configuration(map, defaults);
    }
}