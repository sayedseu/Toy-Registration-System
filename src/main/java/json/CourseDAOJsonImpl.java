package json;

import com.sayed.toyregistrationsystem.DAO;
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
import model.Course;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sayed
 */
public class CourseDAOJsonImpl implements DAO<Course, String> {

    @Override
    public Course insert(Course object) {

        if (retrieve().stream().anyMatch(s -> s.getCode().equals(object.getCode()))) {
            System.out.println("Same Course get");
            return null;
        } else {
            File file = new File("course.json");

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                JSONObject detailJSONObject = new JSONObject();
                detailJSONObject.put("code", object.getCode());
                detailJSONObject.put("title", object.getTitle());
                detailJSONObject.put("credit", String.valueOf(object.getCredit()));
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("course", detailJSONObject);
                fileWriter.write(jSONObject.toJSONString());
                fileWriter.write(System.lineSeparator());
                fileWriter.flush();
                return retrieve(object.getCode());
            } catch (IOException ex) {
                Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public List<Course> retrieve() {
        File file = new File("course.json");
        FileReader reader = null;
        JSONParser jSONParser = new JSONParser();
        List<Course> courses = new ArrayList<>();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject jSONObject = (JSONObject) jSONParser.parse(line);
                JSONObject valueJSONObject = (JSONObject) jSONObject.get("course");
                String code = (String) valueJSONObject.get("code");
                String title = (String) valueJSONObject.get("title");
                String creditString = (String) valueJSONObject.get("credit");
                double credit = Double.valueOf(creditString);
                Course course = new Course(code, title, credit);
                courses.add(course);
            }
            return courses;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Course retrieve(String data) {
        List<Course> courses = retrieve().stream()
                .filter(s -> s.getCode().equals(data))
                .collect(Collectors.toList());
        if (courses.size() != 1) {
            throw new IllegalStateException();
        } else {
            return courses.get(0);
        }
    }

    @Override
    public Course update(String data, Course course) {
        if (retrieve().stream().anyMatch(s -> s.getCode().equals(data))) {

            File file = new File("course.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("course");
                        if (object.get("code").equals(data)) {
                            object.remove("code");
                            object.remove("title");
                            object.remove("credit");
                            object.put("code", course.getCode());
                            object.put("title", course.getTitle());
                            object.put("credit", String.valueOf(course.getCredit()));
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
                    Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return retrieve(course.getCode());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No record found");
        }

        return null;
    }

    @Override
    public boolean delete(String data) {
        if (retrieve().stream().anyMatch(s -> s.getCode().equals(data))) {

            File file = new File("course.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("course");
                        if (object.get("code").equals(data)) {
                            jSONObject.remove("course");
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
                    Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CourseDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else {
            System.out.println("No record found");
        }
        return false;
    }

}
