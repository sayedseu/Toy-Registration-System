package json;

import com.sayed.toyregistrationsystem.DAO;
import com.sayed.toyregistrationsystem.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Faculty;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sayed
 */
public class FacultyDAOJsonImpl implements DAO<Faculty, String> {

    @Override
    public Faculty insert(Faculty object) {

        if (retrieve().stream().anyMatch(s -> s.getInitial().equals(object.getInitial()))) {
            System.out.println("Same Faculty got");
            return null;
        } else {
            File file = new File("faculty.json");

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                JSONObject detailJSONObject = new JSONObject();
                detailJSONObject.put("initial", object.getInitial());
                detailJSONObject.put("name", object.getName());
                detailJSONObject.put("rank", object.getRank());
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("faculty", detailJSONObject);
                fileWriter.write(jSONObject.toJSONString());
                fileWriter.write(System.lineSeparator());
                fileWriter.flush();
                return retrieve(object.getInitial());
            } catch (IOException ex) {
                Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public List<Faculty> retrieve() {
        File file = new File("faculty.json");
        FileReader reader = null;
        JSONParser jSONParser = new JSONParser();
        List<Faculty> facultys = new ArrayList<>();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject jSONObject = (JSONObject) jSONParser.parse(line);
                JSONObject valueJSONObject = (JSONObject) jSONObject.get("faculty");
                String name = (String) valueJSONObject.get("name");
                String initial = (String) valueJSONObject.get("initial");
                String rank = (String) valueJSONObject.get("rank");
                Faculty faculty = new Faculty(initial, name, rank);
                facultys.add(faculty);
            }
            return facultys;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Faculty retrieve(String data) {
        List<Faculty> facultys = retrieve().stream()
                .filter(s -> s.getInitial().equals(data))
                .collect(Collectors.toList());
        if (facultys.size() != 1) {
            throw new IllegalStateException();
        } else {
            return facultys.get(0);
        }
    }

    @Override
    public Faculty update(String data, Faculty faculty) {
        if (retrieve().stream().anyMatch(s -> s.getInitial().equals(data))) {

            File file = new File("faculty.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                RandomAccessFile tempRaf = null;
                try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                    tempRaf = new RandomAccessFile(tempFile, "rw");
                    JSONParser parser = new JSONParser();
                    String line;
                    raf.seek(0);
                    while (raf.getFilePointer() < raf.length()) {
                        line = raf.readLine();
                        JSONObject jSONObject = (JSONObject) parser.parse(line);
                        JSONObject object = (JSONObject) jSONObject.get("faculty");
                        if (object.get("initial").equals(data)) {
                            object.remove("initial");
                            object.remove("name");
                            object.remove("rank");
                            object.put("initial", faculty.getInitial());
                            object.put("name", faculty.getName());
                            object.put("rank", faculty.getRank());
                        }
                        tempRaf.writeBytes(jSONObject.toJSONString());
                        tempRaf.writeBytes(System.lineSeparator());
                    }
                    raf.seek(0);
                    tempRaf.seek(0);
                    while (tempRaf.getFilePointer() < tempRaf.length()) {
                        raf.writeBytes(tempRaf.readLine());
                        raf.writeBytes(System.lineSeparator());
                    }
                    raf.setLength(tempRaf.length());
                } catch (ParseException ex) {
                    Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return retrieve(faculty.getInitial());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No record found");
        }

        return null;
    }

    @Override
    public boolean delete(String data) {
        if (retrieve().stream().anyMatch(s -> s.getInitial().equals(data))) {

            File file = new File("faculty.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                RandomAccessFile tempRaf = null;
                try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                    tempRaf = new RandomAccessFile(tempFile, "rw");
                    String line;
                    raf.seek(0);
                    JSONParser parser = new JSONParser();
                    while (raf.getFilePointer() < raf.length()) {
                        line = raf.readLine();
                        JSONObject jSONObject = (JSONObject) parser.parse(line);
                        JSONObject object = (JSONObject) jSONObject.get("faculty");
                        if (object.get("initial").equals(data)) {
                            jSONObject.remove("faculty");
                            continue;
                        }
                        tempRaf.writeBytes(jSONObject.toJSONString());
                        tempRaf.writeBytes(System.lineSeparator());
                    }
                    raf.seek(0);
                    tempRaf.seek(0);
                    while (tempRaf.getFilePointer() < tempRaf.length()) {
                        raf.writeBytes(tempRaf.readLine());
                        raf.writeBytes(System.lineSeparator());
                    }
                    raf.setLength(tempRaf.length());
                } catch (ParseException ex) {
                    Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacultyDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else {
            System.out.println("No record found");
        }
        return false;
    }

}
