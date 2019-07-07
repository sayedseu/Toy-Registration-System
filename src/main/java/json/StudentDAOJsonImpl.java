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
import model.Student;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sayed
 */
public class StudentDAOJsonImpl implements DAO<Student, String> {

    @Override
    public Student insert(Student object) {

        if (retrieve().stream().anyMatch(s -> s.getId().equals(object.getId()))) {
            System.out.println("Same student got");
            return null;
        } else {
            File file = new File("student.json");

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                JSONObject detailJSONObject = new JSONObject();
                detailJSONObject.put("id", object.getId());
                detailJSONObject.put("name", object.getName());
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("student", detailJSONObject);
                fileWriter.write(jSONObject.toJSONString());
                fileWriter.write(System.lineSeparator());
                fileWriter.flush();
                return retrieve(object.getId());
            } catch (IOException ex) {
                Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public List<Student> retrieve() {
        File file = new File("student.json");
        FileReader reader = null;
        JSONParser jSONParser = new JSONParser();
        List<Student> students = new ArrayList<>();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject jSONObject = (JSONObject) jSONParser.parse(line);
                JSONObject valueJSONObject = (JSONObject) jSONObject.get("student");
                String name = (String) valueJSONObject.get("name");
                String id = (String) valueJSONObject.get("id");
                Student student = new Student(id, name);
                System.out.println(student);
                students.add(student);
            }
            return students;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Student retrieve(String data) {
        List<Student> studentList = retrieve().stream()
                .filter(s -> s.getId().equals(data))
                .collect(Collectors.toList());
        if (studentList.size() != 1) {
            throw new IllegalStateException();
        } else {
            return studentList.get(0);
        }
    }

    @Override
    public Student update(String data, Student student) {
        if (retrieve().stream().anyMatch(s -> s.getId().equals(data))) {

            File file = new File("student.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("student");
                        if (object.get("id").equals(data)) {
                            object.remove("id");
                            object.remove("name");
                            object.put("id", student.getId());
                            object.put("name", student.getName());
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
                    Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return retrieve(student.getId());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return retrieve(student.getId());
        } else {
            System.out.println("No record found");
        }

        return null;
    }

    @Override
    public boolean delete(String data) {

        if (retrieve().stream().anyMatch(s -> s.getId().equals(data))) {

            File file = new File("student.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("student");
                        if (object.get("id").equals(data)) {
                            jSONObject.remove("student");
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
                    Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StudentDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else {
            System.out.println("No record found");
        }
        return false;
    }

}
