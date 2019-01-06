package propra2.person;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMainPage() throws Exception{
        File login = new ClassPathResource("index.html").getFile();
        String html = new String(Files.readAllBytes(login.toPath()));

        this.mockMvc.perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(html))
                .andDo(print());
    }

    @Autowired
    private PersonController controller;

    @Test
    public void contexLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    public void addToDatabase() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void index() {
    }

    @Test
    public void saveChanges() {
    }
}