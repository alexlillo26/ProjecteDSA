package edu.upc.dsa.orm.dao;

import edu.upc.dsa.models.User;
import edu.upc.dsa.orm.FactorySession;
import edu.upc.dsa.orm.dao.UserDAO;
import edu.upc.dsa.orm.dao.UserDAOImpl;
import edu.upc.dsa.orm.Session;
import edu.upc.dsa.models.levels;


import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public String addUser(String id, String Username, String password) {
        Session session = null;
        int result = 0;
        try {
            session = FactorySession.openSession();
            User user = new User(Username, password, "notadmin");
            result = session.save(user);
        }
        catch (Exception e) {
            return "Error";
        }
        finally {
            session.close();
        }

        if (result == -1) {
            return "Error";
        }
        else {
            return id;
        }

    }

    @Override
    public String addLevel(String levelName, String level) {
        Session session = null;
        int result = 0;
        try {
            session = FactorySession.openSession();
            levels Level = new levels(levelName, level);
            result = session.save(Level);
        }
        catch (Exception e) {
            return "Error";
        }
        finally {
            session.close();
        }

        if (result == -1) {
            return "Error";
        }
        else {
            return "Correct";
        }

    }

    @Override
    public int updateUserCoins(String name, int coins) {
        Session session = null;
        int result = 0;
        try {
            session = FactorySession.openSession();
            User user = (User) session.get(User.class, name);
            if (user != null) {
                user.setCoins(coins);
                session.update(user);
                result = 1;
            }
        } catch (Exception e) {
            // LOG
            result = -1;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public int updateUserPotions(String name, float JumpPotions, float SpeedPotions, float MaxHealthPotions, float AttackSpeedPotions) {
        Session session = null;
        int result = 0;
        try {
            session = FactorySession.openSession();
            User user = (User) session.get(User.class, name);
            if (user != null) {
                user.setJumpPotions(JumpPotions);
                user.setSpeedPotions(SpeedPotions);
                user.setMaxHealthPotions(MaxHealthPotions);
                user.setAttackSpeedPotions(AttackSpeedPotions);
                session.update(user);
                result = 1;
            }
        } catch (Exception e) {
            // LOG
            result = -1;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public User getUserbyID(String ID) {
        Session session = null;
        User user = null;
        try {
            session = FactorySession.openSession();
            user = (User) session.get(User.class, ID);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }
    @Override
    public levels getlevel(String name)
    {
        Session session = null;
        levels level = null;
        try {
            session = FactorySession.openSession();
            level = (levels) session.get(levels.class, name);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return level;
    }

    @Override
    public User getUserbyName(User userprovided) {
        Session session = null;
        User user = null;
        try {
            session = FactorySession.openSession();
            user = (User) session.getbyName(User.class, userprovided.getUsername());
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    public void updateUserPartida(String name, String partida)
    {
        Session session = null;
        int result;
        try {
            session = FactorySession.openSession();
            User user = (User) session.getbyName(User.class, name);
            if (user != null) {
                user.setPartida(partida);
                session.update(user);
                result = 1;
            }
        } catch (Exception e) {
            // LOG
            result = -1;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateUser(User usser) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            User user = (User) session.get(User.class, usser.getId());
            if (user != null) {
                user.setId(usser.getId());
                user.setFullName(usser.getFullName());
                user.setAge(usser.getAge());
                user.setEmail(usser.getEmail());
                user.setProfilePicture(usser.getProfilePicture());
                user.setId(usser.getId());
                user.setCoins(usser.getCoins());
                user.setIsAdmin(usser.getIsAdmin());
                user.setUsername(usser.getUsername());
                user.setPassword(usser.getPassword());

                session.update(user);
            }
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUserbyID(String ID) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            User user = (User) session.get(User.class, ID);
            if (user != null) {
                session.delete(user);
            }
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getUsers() {
        Session session = null;
        List<User> users = null;
        try {
            session = FactorySession.openSession();
            users = session.findAll(User.class);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }


}
