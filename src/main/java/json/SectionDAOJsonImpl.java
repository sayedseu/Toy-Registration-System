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
import model.Section;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sayed
 */
public class SectionDAOJsonImpl implements DAO<Section, Integer> {

    @Override
    public Section insert(Section object) {

        if (retrieve().stream().anyMatch(s -> s.getSectionID() == (object.getSectionID()))) {
            System.out.println("Same Section get");
            return null;
        } else {
            File file = new File("section.json");

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                JSONObject detailJSONObject = new JSONObject();
                detailJSONObject.put("sectionID", String.valueOf(object.getSectionID()));
                detailJSONObject.put("sectionNumber", String.valueOf(object.getSectionNumber()));
                detailJSONObject.put("semesterNumber", String.valueOf(object.getSemesterNumber()));
                detailJSONObject.put("seatLimit", String.valueOf(object.getSeatLimit()));
                detailJSONObject.put("code", object.getCode());
                detailJSONObject.put("initial", object.getInitial());
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("section", detailJSONObject);
                fileWriter.write(jSONObject.toJSONString());
                fileWriter.write(System.lineSeparator());
                fileWriter.flush();
                return retrieve(object.getSectionID());
            } catch (IOException ex) {
                Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public List<Section> retrieve() {
        File file = new File("section.json");
        FileReader reader = null;
        JSONParser jSONParser = new JSONParser();
        List<Section> sections = new ArrayList<>();
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject jSONObject = (JSONObject) jSONParser.parse(line);
                JSONObject valueJSONObject = (JSONObject) jSONObject.get("section");
                String sectionIDString = (String) valueJSONObject.get("sectionID");
                String sectionNumberString = (String) valueJSONObject.get("sectionNumber");
                String semesterNumberString = (String) valueJSONObject.get("semesterNumber");
                String seatLimitString = (String) valueJSONObject.get("seatLimit");
                String code = (String) valueJSONObject.get("code");
                String initial = (String) valueJSONObject.get("initial");
                int sectionID = Integer.parseInt(sectionIDString);
                int sectionNumber = Integer.parseInt(sectionNumberString);
                int semesterNumber = Integer.parseInt(semesterNumberString);
                int seatLimit = Integer.parseInt(seatLimitString);
                Section section = new Section(sectionID, sectionNumber, semesterNumber, seatLimit, code, initial);
                sections.add(section);
            }
            return sections;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Section retrieve(Integer data) {
        List<Section> sections = retrieve().stream()
                .filter(s -> s.getSectionID() == data)
                .collect(Collectors.toList());
        if (sections.size() != 1) {
            throw new IllegalStateException();
        } else {
            return sections.get(0);
        }
    }

    @Override
    public Section update(Integer data, Section section) {

        if (retrieve().stream().anyMatch(s -> s.getSectionID() == data)) {

            File file = new File("section.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("section");
                        if (object.get("sectionID").equals(String.valueOf(data))) {
                            object.remove("sectionID");
                            object.remove("sectionNumber");
                            object.remove("semesterNumber");
                            object.remove("seatLimit");
                            object.remove("code");
                            object.remove("initial");
                            object.put("sectionID", String.valueOf(section.getSectionID()));
                            object.put("sectionNumber", String.valueOf(section.getSectionNumber()));
                            object.put("semesterNumber", String.valueOf(section.getSemesterNumber()));
                            object.put("seatLimit", String.valueOf(section.getSeatLimit()));
                            object.put("code", section.getCode());
                            object.put("initial", section.getInitial());

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
                    Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return retrieve(section.getSectionID());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No record found");
        }

        return null;
    }

    @Override
    public boolean delete(Integer data) {
        if (retrieve().stream().anyMatch(s -> s.getSectionID() == data)) {

            File file = new File("section.json");
            File tempFile = new File("temp.json");
            if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                        JSONObject object = (JSONObject) jSONObject.get("section");
                        if (object.get("sectionID").equals(String.valueOf(data))) {
                            jSONObject.remove("section");
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
                    Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                tempRaf.close();
                tempFile.delete();
                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SectionDAOJsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else {
            System.out.println("No record found");
        }
        return false;
    }

}
