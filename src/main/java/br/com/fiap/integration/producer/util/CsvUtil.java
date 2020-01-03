package br.com.fiap.integration.producer.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Configuration
@AllArgsConstructor
public class CsvUtil {

//    @Value("classPath:/input/inputData.csv")
//    private Resource inputResource;

    public <T> List<T> loadObjectList(Class<T> type) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = ResourceUtils.getFile("classPath:input/inputData.csv");
//            File file = inputResource.getFile();
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            System.out.println("Error occurred while loading object list from file");
            return Collections.emptyList();
        }
    }

}
