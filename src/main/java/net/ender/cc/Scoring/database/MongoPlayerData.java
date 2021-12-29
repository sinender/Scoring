package net.ender.cc.Scoring.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoPlayerData {

    private static MongoCollection<Document> col;

    public void connect() {
        MongoClient client = MongoClients.create("mongodb+srv://admin:SF5R8UH5G3ZeVnUk@cluster0.gfcdb.mongodb.net/test");
        MongoDatabase database = client.getDatabase("Tournament");
        col = database.getCollection("playerdata");
    }

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

    public Object getData(UUID uuid, String key) {
        Bson query = eq("uuid", uuid.toString());
        if (col.find(query).first() == null) {
            setData(uuid, key, null);
        }
        return col.find(query);
    }

    public Document getPlayerDocument(UUID uuid) {
        return col.find(eq("uuid", uuid.toString())).first();
    }

    
    public boolean remove(UUID uuid) {
        Bson query = eq("uuid", uuid);
        Document found = col.find(query).first();
        if (found == null) return false;

        col.deleteOne(found);
        return true;
    }

    public void createPlayerData(UUID uuid) {
        Document found = col.find(eq("uuid", uuid.toString())).first();
        if (found != null) return;

        setData(uuid, "totalScore", 0);
    }

    
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
