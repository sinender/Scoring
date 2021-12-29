package net.ender.cc.Scoring.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoPlayerData implements MongoDB {

    private static boolean connected = false;

    private static MongoCollection<Document> col;

    public MongoPlayerData() {

    }


    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void connect() {
        MongoClient client = MongoClients.create("mongodb+srv://admin:SF5R8UH5G3ZeVnUk@cluster0.gfcdb.mongodb.net/test");

        MongoDatabase database = client.getDatabase("Tournament");
        col = database.getCollection("playerdata");
        System.out.println(col);
        Bukkit.getLogger().info("Successfully connected to MongoDB!");
        connected = true;
    }

    @Override
    public void setData(UUID uuid, String key, Object value) {
        Document query = new Document("uuid", uuid.toString());
        Document found = col.find(query).first();

        if (found == null) {
            Document update = new Document("uuid", uuid.toString());
            update.append(key, value);

            col.insertOne(update);
            return;
        }

        col.updateOne(eq("uuid", uuid.toString()), Updates.set(key, value));
    }

    public void putIfAbsent(UUID uuid, String key, Object value) {
        Document query = new Document("uuid", uuid.toString());
        Document found = col.find(query).first();

        if (found == null) {
            Document update = new Document("uuid", uuid.toString());
            update.append(key, value);

            col.insertOne(update);
            return;
        }
        if(found.get(key) == null) {
            col.updateOne(eq("uuid", uuid.toString()), Updates.set(key, value));
        }
    }

    @Override
    public Object getData(UUID uuid, String key) {

        Document query = new Document("uuid", uuid.toString());
        if (query.isEmpty()) {
            setData(uuid, key, 0D);
        } else {
            if (col.find(query).first() != null) {
                Document found = col.find(query).first();
                if (found.get(key) != null) {
                    return found.get(key);
                } else {
                    setData(uuid, key, 0D);
                    Document found2 = col.find(query).first();
                    return found2.get(key);
                }

            } else {
                setData(uuid, key, 0D);
                if (col.find(query).first() != null) {
                    Document found2 = col.find(query).first();
                    return found2.get(key);
                }
            }

        }
        return null;
    }

    public Document getDocument(UUID uuid, String key) {

        Document query = new Document("uuid", uuid.toString());
        if (query.isEmpty()) {
            setData(uuid, key, 0D);
        } else {
            if (col.find(query).first() != null) {
                Document found = col.find(query).first();
                if (found != null) {
                    if (found.get(key) != null) {
                        return (Document) found.get(key);
                    } else {
                        setData(uuid, key, new Document());
                        Document found2 = col.find(query).first();
                        if (found2 != null)
                            return (Document) found2.get(key);
                    }
                }
            } else {
                setData(uuid, key, new Document());
                if (col.find(query).first() != null) {
                    Document found2 = col.find(query).first();
                    if (found2 != null) {
                        return (Document) found2.get(key);
                    }
                }
            }

        }
        return null;
    }

    public Document getPlayerDocument(UUID uuid) {

        Document query = new Document("uuid", uuid.toString());
        if (col.find(query).first() != null) {
            Document found = col.find(query).first();
            if (found != null) {
                return found;
            }
        }
        return query;
    }

    @Override
    public boolean remove(UUID uuid) {
        Document query = new Document("uuid", uuid.toString());
        Document found = col.find(query).first();

        if (found == null) return false;

        col.deleteOne(found);
        return true;
    }

    public void createPlayerData(UUID uuid) {
        Document query = new Document("uuid", uuid.toString());
        Document found = col.find(query).first();

        if (found != null) return;

        setData(uuid, "totalScore", 0);
    }

    @Override
    public List<Document> getAllDocuments() {
        FindIterable<Document> docs = col.find();
        MongoCursor<Document> cursor = docs.iterator();

        List<Document> found = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                found.add(cursor.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return found;
    }
    public List<Document> getSortedDocuments(String key) {
        FindIterable<Document> docs = col.find().sort(eq(key, ""));
        MongoCursor<Document> cursor = docs.iterator();

        List<Document> found = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                found.add(cursor.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return found;
    }
}
