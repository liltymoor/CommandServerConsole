package org.main.server.fs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.main.server.serializer.ZonedDTSerializer;
import org.shared.model.entity.HumanBeing;
//import org.shared.model.serializer.ZonedDTSerializer;
import org.shared.model.weapon.WeaponType;
import org.shared.network.User;

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
    private static int lastId = 0;

    private LinkedHashSet<HumanBeing> resultSet;
    private HumanDatabaseHelper dbHelper;

    public CollectionIO(HumanDatabaseHelper dbHelper) {
        System.out.format("[HOST] Working Dir: %s%n", System.getProperty("user.dir"));
        if (dbHelper != null) {
            this.dbHelper = dbHelper;
            resultSet = new LinkedHashSet<>(dbHelper.selectAllHuman());
        } else {
            resultSet = new LinkedHashSet<>();
        }

//        dbHelper.registerUser("liltymoor", "123123123");
//
//        Deprecated .JSON collection
//
//
//        System.out.format("[HOST] Collection name: %s%n", collectionName);
//        if (!isCollectionCreated()) {
//            System.out.println("Collection doesn't exist");
//            File file = new File(collectionName);
//            try (FileWriter writer = new FileWriter(file)) {
//                writer.write("{}");
//                writer.flush();
//            } catch (SecurityException | IOException ex) {
//                System.out.println("Can't create collection");
//            }
//        }
//        String jsonString = "";
//        try {
//            fileIStream = new FileInputStream(collectionName);
//
//            bufferedIStream = new BufferedInputStream(fileIStream);
//            jsonString = new String(bufferedIStream.readAllBytes(), StandardCharsets.UTF_8);
//
//            resultSet = parseJson(jsonString);
//            validateList();
//            flushToJson();
//        } catch (FileNotFoundException e) {
//            System.out.println("Can't create IO to collection");
//            ;
//        } catch (IOException e) {
//            System.out.println("Can't read from file (Maybe someone closed the stream)");
//            ;
//        }
//
//        setLastId();
//        //printSuccess();
    }
    // Deprecated
    private boolean validateList() {
        resultSet.removeIf(Objects::isNull);
        return true;
    }
    // Deprecated
    private void setLastId() {
        for (HumanBeing being: resultSet)
            if (being.getId() > lastId)
                lastId = being.getId();
    };
    // Deprecated
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

    public void addToCollection(HumanBeing entity, String username) {
        if (dbHelper.addNewHuman(entity, username)) {
            resultSet.add(entity);
        }
        else
            System.out.println("[COLLECTION] Wasn't edited because of db issues");
    }

    public boolean editCollectionEntity(HumanBeing updated, String username) {
        if (dbHelper.updateHuman(updated, username))
            for (HumanBeing being: resultSet)
                if (being.getId() == updated.getId() && being.getEntityOwner().equals(username)) {
                    ArrayList<HumanBeing> tempList = new ArrayList<>(resultSet);
                    tempList.set(tempList.indexOf(being), updated);
                    resultSet.clear();
                    resultSet.addAll(tempList);
                    return true;
                }
        return false;
    }

    public void removeFromCollection(HumanBeing entity, String username) {
        if(dbHelper.removeBeing(entity.getId(), username))
            resultSet.remove(entity);
        else
            System.out.println("[COLLECTION] Wasn't edited because of db issues");
    }

    public void clearCollection(String username) {
        if (dbHelper.dropBeings(username)) {
            for (HumanBeing being : resultSet)
                if (being.getEntityOwner().equals(username)) {
                    resultSet.remove(being);
                }
        }
        else
            System.out.println("[COLLECTION] Wasn't edited because of db issues");
    }

    public boolean removeFromCollection(int id, String username) {
        if (dbHelper.removeBeing(id, username)) {
            HumanBeing human = resultSet.stream()
                    .filter(humanBeing -> humanBeing.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (human != null) {
                removeFromCollection(human, username);
                return true;
            }
        }
        else
            System.out.println("[COLLECTION] Wasn't edited because of db issues");
        return false;
    }

    public int size() {
        return resultSet.size();
    }

    public void forEach(Consumer<? super HumanBeing> action) {
        resultSet.forEach(action);
    }

    public void removeByWeapon(WeaponType weaponType, String username) {
        if (dbHelper.removeBeings(weaponType, username)){
        List<HumanBeing> removeQueue = resultSet.stream()
                .filter(humanBeing -> humanBeing.getWeaponType() == weaponType && humanBeing.getEntityOwner().equals(username))
                .toList();

        removeQueue.forEach((being) -> {removeFromCollection(being, username);});
        }
        else
            System.out.println("[COLLECTION] Wasn't edited because of db issues");
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

    public RegistrationResult registerUser(User userInfo) {
        return dbHelper.registerUser(userInfo.getUsername(), userInfo.getPassword());
    }

    public UserAuthResult authUser(User userInfo) {
        return dbHelper.validateUser(userInfo.getUsername(), userInfo.getPassword());
    }

    public LinkedHashSet<HumanBeing> getBeings() {
        return resultSet;
    }
}
