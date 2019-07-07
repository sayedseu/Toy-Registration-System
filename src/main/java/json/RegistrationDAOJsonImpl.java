package json;

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
import model.Registration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sayed
 */
public class RegistrationDAOJsonImpl {

    public RegistrationDAOJsonImpl() {
    }

    public Registration insert(Registration registration) {

        if (retrieve(registration.getStudentID()).stream().anyMatch(s -> (s.getStudentID().equals(registration.getStudentID())) && (s.getSectionID() == registration.getSectionID()))) {
            System.out.println("Same Record found");
            return null;
        }

        File file = new File("registration.json");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            JSONObject detailJSONObject = new JSONObject();
            detailJSONObject.put("studentID", registration.getStudentID());
            detailJSONObject.put("sectionID", String.valueOf(registration.getSectionID()));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("registration", detailJSONObject);
            fileWriter.write(jSONObject.toJSONString());
            fileWriter.write(System.lineSeparator());
            fileWriter.flush();
            return retrieve(registration.getStudentID(), registration.getSectionID());
        } catch (IOException ex) {
            Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Registration retrieve(String studentID, int sectionID) {
        List<Registration> registrations = retrieve(studentID).stream()
                .filter(s -> (s.getStudentID().equals(studentID)) && (s.getSectionID() == sectionID))
                .collect(Collectors.toList());
        if (registrations.size() != 1) {
            throw new IllegalStateException();
        } else {
            return registrations.get(0);
        }
    }

    public List<Registration> retrieve(String studentID) {
        File file = new File("registration.json");
        FileReader reader = null;
        JSONParser jSONParser = new JSONParser();
        List<Registration> registrations = new ArrayList<>();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject jSONObject = (JSONObject) jSONParser.parse(line);
                JSONObject valueJSONObject = (JSONObject) jSONObject.get("registration");

                if (valueJSONObject.get("studentID").equals(studentID)) {

                    String studentId = (String) valueJSONObject.get("studentID");
                    String sectionIDString = (String) valueJSONObject.get("sectionID");
                    int sectionId = Integer.parseInt(sectionIDString);
                    Registration registration = new Registration(studentID, sectionId);
                    registrations.add(registration);

                }

            }
            return registrations;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Registration update(String studentID, int sectionID, Registration registration) {

        if (retrieve(studentID).stream().anyMatch(s -> (s.getStudentID().equals(studentID)) && (s.getSectionID() == sectionID))) {

            File file = new File("registration.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("registration");
                        if ((object.get("studentID").equals(studentID)) && (object.get("sectionID").equals(String.valueOf(sectionID)))) {
                            object.remove("studentID");
                            object.remove("sectionID");
                            object.put("studentID", registration.getStudentID());
                            object.put("sectionID", String.valueOf(registration.getSectionID()));
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
                    Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return retrieve(registration.getStudentID(), registration.getSectionID());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RegistrationDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No record found");
        }

        return null;
    }

    public boolean delete(String studentID, int sectionID) {

        if (retrieve(studentID).stream().anyMatch(s -> (s.getStudentID().equals(studentID)) && (s.getSectionID() == sectionID))) {

            File file = new File("registration.json");
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
                        JSONObject object = (JSONObject) jSONObject.get("registration");
                        if ((object.get("studentID").equals(studentID)) && (object.get("sectionID").equals(String.valueOf(sectionID)))) {
                            jSONObject.remove("registration");
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
