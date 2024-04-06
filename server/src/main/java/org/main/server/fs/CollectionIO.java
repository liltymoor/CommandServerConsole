package org.main.server.fs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.shared.model.entity.HumanBeing;
//import org.shared.model.serializer.ZonedDTSerializer;
import org.shared.model.weapon.WeaponType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author lil_timmie
 * Класс для управления коллекцией объектов {@link HumanBeing}.
 */
public class CollectionIO {
    private FileInputStream fileIStream;
    private BufferedInputStream bufferedIStream;
    public static String collectionName = System.getenv("collectionName");

    private static int lastId = 0;

    private LinkedHashSet<HumanBeing> resultSet;

    public CollectionIO() {
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
        String collectionName = CollectionIO.collectionName;
        try {
            FileInputStream stream = new FileInputStream(collectionName);
            return true;
        } catch (IOException | NullPointerException e) {
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
        try (FileWriter fileWriter = new FileWriter(collectionName)){
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            SimpleModule module = new SimpleModule();
            //module.addSerializer(ZonedDateTime.class, new ZonedDTSerializer());
            mapper.registerModule(module);

            String data = mapper.writeValueAsString(resultSet);
            fileWriter.write(data);
            return true;
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public String printCollection() {
        StringBuilder sb = new StringBuilder();
        for (HumanBeing being: resultSet)
            sb.append(being.toString()).append("\n");
        return sb.toString();
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
        HumanBeing human = resultSet.stream()
                .filter(humanBeing -> humanBeing.getId() == id)
                .findFirst()
                .orElse(null);
        if (human != null) {
            removeFromCollection(human);
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
        List<HumanBeing> removeQueue = resultSet.stream()
                .filter(humanBeing -> humanBeing.getWeaponType() == weaponType)
                .toList();

        removeQueue.forEach(this::removeFromCollection);
    }

    public boolean isMinimal(HumanBeing entity) {
        var result = resultSet.stream()
                .filter(human -> human.compareTo(entity) > 0)
                .findFirst()
                .orElse(null);
        return result == null;
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
