package org.main.server.fs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.main.server.model.entity.HumanBeing;
import org.main.server.model.serializer.ZonedDTSerializer;
import org.main.server.model.weapon.WeaponType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author lil_timmie
 * Класс для управления коллекцией объектов {@link HumanBeing}.
 */
public class CollectionIO {
    private FileInputStream fileIStream;
    private BufferedInputStream bufferedIStream;
    private String collectionName;

    private static int lastId = 0;

    private LinkedHashSet<HumanBeing> resultSet;

    public CollectionIO() {
        collectionName = System.getenv("COLLECTION_FILE_NAME");
        if (!isCollectionCreated()) {
            System.out.println("Collection doesn't exist");
            File file = new File(collectionName);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");
                writer.flush();
            } catch (SecurityException | IOException ex) {
                System.out.println("Can't create collection");
            }
        }
        String jsonString = "";
        try {
            fileIStream = new FileInputStream(collectionName);

            bufferedIStream = new BufferedInputStream(fileIStream);
            jsonString = new String(bufferedIStream.readAllBytes(), StandardCharsets.UTF_8);

            resultSet = parseJson(jsonString);
            validateList();
            flushToJson();
        } catch (FileNotFoundException e) {
            System.out.println("Can't create IO to collection");
            ;
        } catch (IOException e) {
            System.out.println("Can't read from file (Maybe someone closed the stream)");
            ;
        }

        setLastId();
        //printSuccess();
    }

    public static boolean isCollectionCreated() {
        int[]a = new int[9];
        Arrays.compare(a,a);
        StringBuilder builder = new StringBuilder();
        builder.append(System.getenv("COLLECTION_FILE_NAME")).append(".json").append(System.lineSeparator());
        builder.append(System.getenv("COLLECTION_FILE_NAME"+".json"+System.lineSeparator()));
        String collectionName = System.getenv("COLLECTION_FILE_NAME");
        String jsonString = "";
        String collefctionName = System.getenv("COLLECTION_FILE_NAME");
        jsonString += collefctionName;
        try (FileInputStream stream = new FileInputStream(collectionName)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isCollectionCreated(String collectionName) {
        try (FileInputStream stream = new FileInputStream(collectionName)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean validateList() {
        resultSet.removeIf(Objects::isNull);
        return true;
    }

    private void setLastId() {
        for (HumanBeing being: resultSet)
            if (being.getId() > lastId)
                lastId = being.getId();
    };

    private static LinkedHashSet<HumanBeing> parseJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (IOException e) {
            // Exception handling
            System.out.println("Failed while parsing json string | " + e);
            return new LinkedHashSet<>();
        }
    }

    public boolean flushToJson() {
        try (FileWriter fileWriter = new FileWriter(System.getenv("COLLECTION_FILE_NAME"));){
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            SimpleModule module = new SimpleModule();
            module.addSerializer(ZonedDateTime.class, new ZonedDTSerializer());
            mapper.registerModule(module);

            String data = mapper.writeValueAsString(resultSet);
            fileWriter.write(data);
            return true;
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public void printSuccess() {
        System.out.println("Collection parsed successfully.");
        System.out.printf("Collection items count %d\n", resultSet.size());
    }

    public void addToCollection(HumanBeing entity) {
        entity.setId(++lastId);
        resultSet.add(entity);
    }

    public boolean editCollectionEntity(HumanBeing updated) {
        for (HumanBeing being: resultSet)
            if (being.getId() == updated.getId()) {
                ArrayList<HumanBeing> tempList = new ArrayList<>(resultSet);
                tempList.set(tempList.indexOf(being), updated);
                resultSet.clear();
                resultSet.addAll(tempList);
                return true;
            }
        return false;
    }

    public void removeFromCollection(HumanBeing entity) {
        resultSet.remove(entity);
    }

    public void clearCollection() {
        for (HumanBeing being: resultSet)
            resultSet.remove(being);
    }

    public boolean removeFromCollection(int id) {
        for (HumanBeing being: resultSet)
            if (being.getId() == id) {
                int idToRemove = being.getId();
                resultSet.remove(being);
                if (idToRemove == lastId)
                    setLastId();
                return true;
            }
        return false;
    }

    public int size() {
        return resultSet.size();
    }

    public String getCollectionName(){
        return collectionName;
    }

    public void forEach(Consumer<? super HumanBeing> action) {
        resultSet.forEach(action);
    }

    public void removeByWeapon(WeaponType weaponType) {
        List<HumanBeing> removeQueue = new ArrayList<>();

        // Mark all entities with weaponType to be removed
        for (HumanBeing being: resultSet)
            if (being.getWeaponType().ordinal() == weaponType.ordinal())
                removeQueue.add(being);

        for (HumanBeing being: removeQueue)
            removeFromCollection(being);
    }

    public boolean isMinimal(HumanBeing entity) {
        for (HumanBeing being: resultSet)
            if (being.compareTo(entity) < 0)
                return false;

        return true;
    }

    public int countImpactSpeedGreater(Long speed) {
        int counter = 0;
        for (HumanBeing being: resultSet) {
            if (being.getImpactSpeed() > speed)
                counter++;
        }
        return counter;
    }

    public HashSet<Long> getUniqueImpact() {
        HashSet<Long> uniqueImpact = new HashSet<>();
        for (HumanBeing being: resultSet)
            uniqueImpact.add(being.getImpactSpeed());
        return uniqueImpact;
    }
}
