package datalayer;

import java.io.*;

public class UniqueIdDao {
    final static int STARTING_INDEX = 1000;

    private static String getFilePath() {
        return DaoUtils.storageDirectoryName() + File.separator + "uniqueId.txt";
    }

    /**
     * Creates the first unique ID and writes it to the disk.
     *
     * @return unique id#
     */
    private static int getVeryFirstId(){
        int uniqueId = STARTING_INDEX;
        try {
            File file = new File(getFilePath());
            file.createNewFile();
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new Integer(uniqueId));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uniqueId;
    }

    /**
     * Lookup the most recently used ID number.
     * Return an id increased by one and update the record
     *
     * @return unique id
     */
    public static int getID(){
        int uniqueId = 0;

        try {
            File file = new File(getFilePath());
            if (!file.exists()) {
                return getVeryFirstId();
            }
            else{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Integer id = (Integer) ois.readObject();
                uniqueId = id.intValue() + 1;
                ois.close();

                // Update the id in the file.
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new Integer(uniqueId));
                oos.close();            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return uniqueId;
    }

    /**
     * Throw away stored unique ID.
     * Will start unique ids from the beginning.
     */
    public static void reset() {
        File file = new File(getFilePath());
        file.delete();
    }

    /**
     * Unit Test
     */
    public static void main(String[] args) {
        UniqueIdDao dao = new UniqueIdDao();
        dao.reset();

        System.out.print("Test 1: ");
        if (dao.getID() == STARTING_INDEX) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }

        System.out.print("Test 2: ");
        if (dao.getID() == STARTING_INDEX+1) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }

}
