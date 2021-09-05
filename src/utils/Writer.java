package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import shop.Shop;

import java.io.FileWriter;
import java.io.IOException;


public class Writer {
    private FileWriter jsonFile;

    public void setJsonFile(String path) throws VariableException.InvalidCommandValueException {
        try {
            if (!Constants.isPathValid(path) || !path.endsWith(".json")) {
                throw new VariableException
                        .InvalidCommandValueException("Invalid path, check to see if it " +
                        "indicates a file or if it is written correctly");
            }
            jsonFile = new FileWriter(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ObjectNode writeFile(Shop shop){
        return shop.storage.toObjectNode();
    }

    public void closeJSON(final ObjectNode object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFile.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
            jsonFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
