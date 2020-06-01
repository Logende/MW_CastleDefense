package org.neubauerfelix.manawars.manawars.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import kotlin.text.Charsets;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonConfiguration extends ConfigurationProvider
{

    private final Gson json = new GsonBuilder().serializeNulls().registerTypeAdapter( Configuration.class,
            (JsonSerializer<Configuration>) (src, typeOfSrc, context) -> context.serialize( src.self )).create();

    @Override
    public void save(Configuration config, String fileName, boolean internal) {
        save(config, internal ? Gdx.files.internal(fileName) : Gdx.files.local(fileName) );
    }

    @Override
    public void save(Configuration config, FileHandle file) {
        try ( Writer writer = new OutputStreamWriter( new FileOutputStream( file.file() ), Charsets.UTF_8 ) )
        {
            save( config, writer );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void save(Configuration config, Writer writer)
    {
        json.toJson( config.self, writer );
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
        return load( reader, null );
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(Reader reader, Configuration defaults)
    {
        Map<String, Object> map = json.fromJson( reader, LinkedHashMap.class );
        if ( map == null )
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration( map, defaults );
    }

    @Override
    public Configuration load(InputStream is)
    {
        return load( is, null );
    }

    @Override
    public Configuration load(InputStream is, Configuration defaults)
    {
        return load( new InputStreamReader( is, Charsets.UTF_8 ), defaults );
    }

    @Override
    public Configuration load(String string)
    {
        return load( string, null );
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration load(String string, Configuration defaults)
    {
        Map<String, Object> map = json.fromJson( string, LinkedHashMap.class );
        if ( map == null )
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration( map, defaults );
    }
}