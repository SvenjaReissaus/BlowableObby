package me.svreissaus.blobby.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.svreissaus.blobby.storage.adapters.EnumTypeAdapter;
import me.svreissaus.blobby.storage.adapters.LocationTypeAdapter;
import java.nio.charset.StandardCharsets;

import javax.tools.DocumentationTool;
import java.io.*;

public final class Store<Entity> {
    private Entity entity;

    private final StorageProvider<Entity> provider;

    public Store(Entity entity, File folder) {
        this(entity, folder, entity.getClass().getSimpleName());
    }

    public Store(Entity entity, File folder, String name) {
        this.entity = entity;
        this.provider = new StorageProvider(this.entity, folder, name);
    }

    public Entity createStore(Class<Entity> store) {
        this.entity = this.provider.loadSave(store);
        return this.entity;
    }

    public Entity loadStore(Class<Entity> store) {
        this.entity = this.provider.load(store);
        return this.entity;
    }

    public Entity saveStore(Entity entity) {
        this.entity = this.provider.save(entity);
        return this.entity;
    }

    private final class StorageProvider<TStore> {
        private final Gson gson = (new GsonBuilder())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(128, 64).registerTypeAdapter(DocumentationTool.Location.class, new LocationTypeAdapter())
                .registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY)
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        private final File file;

        private TStore entity;

        public StorageProvider(TStore entity, File folder, String name) {
            if (!folder.exists())
                folder.mkdirs();
            this.file = new File(folder, name.toLowerCase() + ".json");
            this.entity = entity;
        }

        public TStore loadSave(Class<TStore> store) {
            try {
                if (this.file.exists())
                    return load(store);
                return save(this.entity);
            } catch (Exception exception) {
                exception.printStackTrace();
                return this.entity;
            }
        }

        public TStore load(Class<TStore> store) {
            try {
                String json = read();
                return (TStore)this.gson.fromJson(json, store);
            } catch (Exception exception) {
                exception.printStackTrace();
                return this.entity;
            }
        }

        public TStore save(TStore latest) {
            try {
                write(this.gson.toJson(latest));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return this.entity;
        }

        private void write(String content) throws IOException {
            FileOutputStream out = new FileOutputStream(this.file);
            out.write(content.getBytes(StandardCharsets.UTF_8));
            out.close();
        }

        private String read() throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
            String ret = new String(new byte[0], StandardCharsets.UTF_8);
            String line;
            for (; (line = in.readLine()) != null; ret = ret + line);
            in.close();
            return ret;
        }
    }
}