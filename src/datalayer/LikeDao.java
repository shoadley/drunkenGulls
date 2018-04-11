package datalayer;

import models.LikeModel;
import models.UserModel;

import java.io.*;
import java.util.ArrayList;

public class LikeDao {

    /**
     * Return likes
     */
    public static ArrayList<LikeModel> getLikes() {
        File file = new File(getFilePath());
        return getLikes(file);
    }

    /*
     * Given a user, delete it from storage.
     */
    public static void deleteLikes() {
        File file = new File(getFilePath());
        file.delete();
    }

    public static int getNumberOfLikes(int storyId) {
        int nLikes = 0;
        ArrayList<LikeModel> likes = getLikes();

        for (LikeModel like: likes) {
            if (like.getStoryId() == storyId)
                nLikes++;
        }

        return nLikes;
    }


    public static boolean didUserLikeStory(int storyId, String username) {
        ArrayList<LikeModel> likes = getLikes();
        for (LikeModel like: likes) {
            if (like.getStoryId() == storyId && like.getUsername().equalsIgnoreCase(username))
                return true;
        }

        return false;
    }
    /*
     * Save the given user model.
     */
    public static void saveLikes(ArrayList<LikeModel> likes){
        try {
            File file = new File(getFilePath());
            file.createNewFile();
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(likes);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLike(int storyId, String username) {
        if (didUserLikeStory(storyId, username)) {
            return;
        }

        int nLikes = 0;
        ArrayList<LikeModel> likes = getLikes();
        likes.add(new LikeModel(storyId, username));
        saveLikes(likes);
    }

    /**
     * Given a story ID, where are we saving it to storage (file name)?
     */
    private static String getFilePath() {
        return DaoUtils.storageDirectoryName() + File.separator + "likes" + ".txt";
    }

    /*
     * Given a username, return the user that's saved in the file.
     * Returns null if not found.
     */
    private static ArrayList<LikeModel> getLikes(File file) {
        ArrayList<LikeModel> likes = null;
        try {
            likes = new ArrayList<LikeModel>();

            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                likes = (ArrayList<LikeModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }

        return likes;
    }

    /**
     * Unit test program.
     *
     * @param args
     */
    public static void main(String[] args) {
        testUserDao();
    }

    private static void testUserDao() {
        LikeDao dao = new LikeDao();
        ArrayList<LikeModel> likes = new ArrayList<LikeModel> ();
        LikeModel like = new LikeModel(1,"sam");
        likes.add(like);
        dao.saveLikes(likes);

        likes = dao.getLikes();
        assert(likes != null);
        assert(likes.size() == 1);
        assert(getNumberOfLikes(1) == 1);

        dao.deleteLikes();
    }

}
