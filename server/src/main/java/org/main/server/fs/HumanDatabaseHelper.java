package org.main.server.fs;

import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HumanDatabaseHelper {
    private Connection connection;
    private final ReadWriteLock lock;

    private HumanDatabaseHelper(Connection connection) {
        lock = new ReentrantReadWriteLock();
        this.connection = connection;
    }


    public static HumanDatabaseHelper getDatabaseHelper() {
        try {
            Properties info = new Properties();
            info.load(new FileInputStream("db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cmd_server", info);
            System.out.println("[DB_HELPER] database initialization success");
            return new HumanDatabaseHelper(connection);
        } catch (SQLException ex) {
            System.out.println("[DB_HELPER] Can't connect to db | " + ex.getMessage());
            return null;
        } catch (IOException ex) {
            System.out.println("[DB_HELPER] db.cfg not found");
            return null;
        }
    }

    public LinkedHashSet<HumanBeing> selectAllHuman() {
        LinkedHashSet<HumanBeing> humanList = new LinkedHashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM humanbeing;")) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                HumanBeing being = new HumanBeing(
                        result.getString("name"),
                        new Coordinates(result.getFloat("coord_x"), result.getLong("coord_y")),
                        ZonedDateTime.parse(result.getString("zoneddt")),
                        result.getBoolean("realhero"),
                        result.getBoolean("hastoothpick"),
                        result.getLong("impactspeed"),
                        result.getDouble("minutesofwaiting"),
                        WeaponType.valueOf(result.getString("human_weapon")),
                        Mood.valueOf(result.getString("human_mood")),
                        new Car(result.getString("carname")),
                        result.getString("entityowner")
                );
                being.setId(result.getInt("id"));
                humanList.add(being);
            }
            result.close();
            return humanList;
        } catch (SQLException ex) {
            System.out.println("[DB_HELPER] Error: " + ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new LinkedHashSet<>();
    }


    public int addNewHuman(HumanBeing being, String username) {
        lock.writeLock().lock();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO humanbeing (" +
                "name, coord_x, coord_y, zoneddt, realhero, hastoothpick, impactspeed, minutesofwaiting, human_weapon, human_mood, carname, entityowner" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, being.getName());
            statement.setFloat(2, being.getCoords().getX());
            statement.setLong(3, being.getCoords().getY());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'");
            String formattedString = being.getZonedDT().format(formatter);

            statement.setString(4, formattedString);
            statement.setBoolean(5, being.getRealHero());
            statement.setBoolean(6, being.getHasToothpick());
            statement.setLong(7, being.getImpactSpeed());
            statement.setDouble(8, being.getMinutesOfWaiting());
            statement.setObject(9, being.getWeaponType().name(), Types.OTHER);
            statement.setObject(10, being.getMood().name(), Types.OTHER);
            statement.setString(11, being.getModelCar().getName());
            statement.setString(12, username);
            int updatedRows = statement.executeUpdate();
            lock.writeLock().unlock();
            if (updatedRows < 1) return -1;
        } catch (SQLException ex) {
            lock.writeLock().unlock();
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return -1;
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT last_value FROM humanbeing_id_seq;")) {
            ResultSet result = statement.executeQuery();
            if (result.next()) return result.getInt("last_value");
            System.out.println("[DB_HELPER] Something went wrong | " + new UnknownError().getMessage());
            return -1;
        } catch (SQLException e) {
            System.out.println("[DB_HELPER] Exception caught: " + e.getMessage());
            return -1;
        }
    }

    public boolean updateHuman(HumanBeing being, String username) {
        lock.writeLock().lock();
        try(PreparedStatement statement = connection.prepareStatement("UPDATE humanbeing SET name=?, coord_x=?, coord_y=?, zoneddt=?, realhero=?, hastoothpick=?, impactspeed=?, minutesofwaiting=?, human_weapon=?, human_mood=?, carname=?, entityowner=? WHERE id=?" +
                //" AND entityowner=?" +
                ";"))
        {
            statement.setString(1, being.getName());
            statement.setFloat(2, being.getCoords().getX());
            statement.setLong(3, being.getCoords().getY());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'");
            String formattedString = being.getZonedDT().format(formatter);

            statement.setString(4, formattedString);
            statement.setBoolean(5, being.getRealHero());
            statement.setBoolean(6, being.getHasToothpick());
            statement.setLong(7, being.getImpactSpeed());
            statement.setDouble(8, being.getMinutesOfWaiting());
            statement.setObject(9, being.getWeaponType().name(), Types.OTHER);
            statement.setObject(10, being.getMood().name(), Types.OTHER);
            statement.setString(11, being.getModelCar().getName());
            statement.setString(12, username);
            statement.setInt(13, being.getId());
            // TODO мб надо вернуть работу только с данными юзера
            //statement.setString(14, username);

            int updatedRows = statement.executeUpdate();
            lock.writeLock().unlock();
            return updatedRows > 0;

        } catch (SQLException ex) {
            lock.writeLock().unlock();
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return false;
        }
    }

    public boolean removeBeing(int id, String username) {
        lock.writeLock().lock();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM humanbeing WHERE id=? AND entityowner=?;")) {
            statement.setInt(1, id);
            statement.setString(2, username);
            int updated = statement.executeUpdate();
            return updated > 0;
        } catch (SQLException ex) {
            lock.writeLock().unlock();
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return false;
        }
    }

    public boolean removeBeings(WeaponType type, String username) {
        lock.writeLock().lock();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM humanbeing WHERE human_weapon=? AND entityowner=?;")) {
            statement.setObject(1, type.name(), Types.OTHER);
            statement.setString(2, username);
            int updated = statement.executeUpdate();
            lock.writeLock().unlock();
            return updated > 0;
        } catch (SQLException ex) {
            lock.writeLock().unlock();
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return false;
        }
    }

    public boolean dropBeings(String username) {
        lock.writeLock().lock();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM humanbeing WHERE entityowner=?")) {
            statement.setString(1, username);
            int updated = statement.executeUpdate();
            lock.writeLock().unlock();
            return updated > 0;
        } catch (SQLException ex) {
            lock.writeLock().unlock();
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return false;
        }
    }

    public UserAuthResult validateUser(String user, String password) {
        if (user.isEmpty()) return UserAuthResult.EMPTY_FIELDS;
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
            statement.setString(1, user);

            ResultSet result = statement.executeQuery();
            result.next();

            if (result.getInt("count") == 1) {
                // user exists
                if (checkPass(user, password))
                    return UserAuthResult.SUCCESS;

                return UserAuthResult.WRONG_PASSWORD;
            } else {
                // user doesn't exists
                System.out.println("[DB_HELPER] user not exists");
                return UserAuthResult.NOT_EXISTS;
            }
        } catch (SQLException ex) {
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return UserAuthResult.UNKNOWN_ERROR;
        }
    }

    private boolean checkPass(String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT (passwordHash = crypt(?, passwordHash)) AS password_match FROM users WHERE username=?;")) {

            statement.setString(1, password);
            statement.setString(2, username);

            ResultSet result = statement.executeQuery();
            result.next();

            return result.getBoolean("password_match");
        } catch (SQLException ex) {
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return false;
        }
    }

    public RegistrationResult registerUser(String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, passwordHash) VALUES (?, crypt(?, gen_salt('md5')));")){

            statement.setString(1, username);
            statement.setString(2, password);

            if (statement.executeUpdate() > 0)
                return RegistrationResult.SUCCESS;

            return RegistrationResult.UNKNOWN_ERROR;
        } catch (SQLException ex) {
            System.out.println("[DB_HELPER] Exception caught: " + ex.getMessage());
            return RegistrationResult.USERNAME_EXISTS;
        }
    }
}
