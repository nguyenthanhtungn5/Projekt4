package propra2.person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import propra2.person.Controller.PersonController;
import propra2.person.Model.Projekt;
import propra2.person.Repository.ProjektRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class PersonControllerJunitTests {
	private MockMvc mockMvc;
//	@Autowired
//	PersonRepository personRepository;
	PersonController controller;
	@Mock
	ProjektRepository projektRepository;
//	@Before
//	public void setUpPersonRepo(){
//		Person firstPerson = new Person();
//		firstPerson.setVorname("Tom");
//		firstPerson.setVorname("Stark");
//		firstPerson.setJahreslohn("10000");
//		firstPerson.setKontakt("tung@gmail.com");
//		firstPerson.setSkills(new String[]{"Java","Python"});
//		firstPerson.setProjekteId(new Long[]{Long.valueOf(1),Long.valueOf(2)});
//
//		when(personRepository.findAll()).thenReturn(Arrays.asList(firstPerson));
//	}
	@Before
	public void setUpProjektRepo(){
		//
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");


		//
		controller=new PersonController(projektRepository);
		Projekt firstProjekt = new Projekt();
		firstProjekt.setTitel("projekt4");
		firstProjekt.setBeschreibung("description");
		firstProjekt.setStartdatum("30.10.2018");
		firstProjekt.setLaufzeit("10 Monaten");

		when(projektRepository.findAll()).thenReturn(Arrays.asList(firstProjekt));

		//
		this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(projektRepository))
				.setViewResolvers(viewResolver)
				.build();
	}

	@Test
	public void AddPerson() throws Exception {
		mockMvc.perform(get("/addPerson"))
				.andExpect(status().isOk())
				.andExpect(view().name("addPerson"))
				.andExpect(model().attribute("projekte",hasSize(1)))
				.andExpect(model().attribute("projekte", hasItem(
						allOf(
								hasProperty("titel",is("projekt4")),
								hasProperty("beschreibung",is("description")),
								hasProperty("startdatum",is("30.10.2018")),
								hasProperty("laufzeit",is("10 Monaten"))
						)
				)));
		verify(projektRepository, times(1)).findAll();
		verifyNoMoreInteractions(projektRepository);
	}

}

