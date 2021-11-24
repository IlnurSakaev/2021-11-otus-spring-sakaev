package ru.isakaev.dao;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Dao implementation
 */
public class QuestionDaoImpl implements QuestionDao {

    private final String FILE_NAME;


    public QuestionDaoImpl(String file_name) {
        FILE_NAME = file_name;
    }


    @Override
    public File getFile() {
        URL systemResource = ClassLoader.getSystemResource(FILE_NAME);
        File file = null;
        try {
            file= new File(systemResource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }
}
