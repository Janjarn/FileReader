import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PersonFileManager {

    private static final int ID_SIZE = Integer.BYTES; // 4
    private static final int NAME_SIZE = 50;
    private static final int RECORD_SIZE = ID_SIZE + NAME_SIZE;
    private static final int EMPTY_ID = -1;

    private File file;

    public PersonFileManager(String fileName) {
        this.file = new File(fileName);
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                Person p = readPersonFromRAF(raf);
                if(p.getId()!=EMPTY_ID)
                    persons.add(p);
            }
            return persons;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    private Person readPersonFromRAF(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        byte[] bytes = new byte[NAME_SIZE];
        raf.read(bytes);
        String name = new String(bytes).trim();
        Person p = new Person(id, name);
        return p;
    }

    public void deletePerson(Person p){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                if (id == p.getId()) {
                    raf.seek(raf.getFilePointer()-ID_SIZE);
                    raf.writeInt(EMPTY_ID);
                    return;
                }
                else
                    raf.skipBytes(NAME_SIZE);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Person getPerson(int id){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int currentId = raf.readInt();
                if (currentId == id) {
                    byte[] bytes = new byte[NAME_SIZE];
                    raf.read(bytes);
                    String name = new String(bytes).trim();
                    Person p = new Person(id, name);
                    return p;
                }
                else
                    raf.skipBytes(NAME_SIZE);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public void addPerson(Person p) throws Exception {
        if(p.getId()==EMPTY_ID)
            throw new Exception("-1 Not allowed as id");
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(raf.length()); // Go to end of file
            raf.writeInt(p.getId());
            raf.writeBytes(String.format("%-" + NAME_SIZE + "s", p.getName()).substring(0, NAME_SIZE));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void updatePerson(Person p) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                if (id == p.getId()) {
                    raf.writeBytes(String.format("%-" + NAME_SIZE + "s", p.getName()).substring(0, NAME_SIZE));
                    return;
                }
                else
                    raf.skipBytes(NAME_SIZE);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
