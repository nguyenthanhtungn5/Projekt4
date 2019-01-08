package propra2.person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import propra2.person.Controller.PersonController;
import propra2.person.Model.Person;
import propra2.person.Model.Projekt;
import propra2.person.Repository.PersonRepository;
import propra2.person.Repository.ProjektRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class PersonControllerJunitTests {
	private PersonController controller;
	private MockMvc mockMvc;
	private ArrayList<Person> personDummyREPO= new ArrayList<>();
	private ArrayList<Projekt> projektDummyREPO= new ArrayList<>();
	@Mock
    PersonRepository personRepository;
	@Mock
    ProjektRepository projektRepository;
	@Before
	public void setUp(){
		//Person erzeugen
		Person firstPerson = new Person();
		firstPerson.setVorname("Tom");
		firstPerson.setNachname("Stark");
		firstPerson.setJahreslohn("10000");
		firstPerson.setKontakt("tung@gmail.com");
		firstPerson.setSkills(new String[]{"Java","Python"});
		firstPerson.setProjekteId(new Long[]{1L, 2L});
		// when
		when(personRepository.findAll()).thenReturn(Arrays.asList(firstPerson));
		when(personRepository.findAllById(1L)).thenReturn(Collections.singletonList(firstPerson));
		when(personRepository.add(any(Person.class))).thenReturn(personDummyREPO.add(firstPerson));


		// wichtig für get url
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");
		// Projekt erzeugen
		Projekt firstProjekt = new Projekt();
		firstProjekt.setTitel("projekt4");
		firstProjekt.setBeschreibung("description");
		firstProjekt.setStartdatum("30.10.2018");
		firstProjekt.setLaufzeit("10 Monaten");
		//when
		when(projektRepository.findAll()).thenReturn(Arrays.asList(firstProjekt));
		when(projektRepository.deleteById(1L)).thenReturn(projektDummyREPO=null);
		when(projektRepository.save(any(Projekt.class))).thenReturn(projektDummyREPO.add(firstProjekt));

		// build mockmvc
		controller=new PersonController(projektRepository,personRepository);
		this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(projektRepository,personRepository))
				.setViewResolvers(viewResolver)
				.build();
	}

	@Test
	public void AddPersonPageTEST() throws Exception {
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
	//TODO How to inject REQUESTPARRAM?
	@Test
	public void AddToDatabaseTEST() throws Exception {
		mockMvc.perform(get("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.param("vorname","Tom")
							.param("nachname","Stark")
							.param("jahreslohn","10000")
							.param("kontakt","tung@gmail.com")
							.param("projekteId", Arrays.toString(new Long[]{1L, 2L}))
							.param("skills", "Java","Python"))
				.andExpect(status().isOk())
				.andExpect(view().name("confirmationAdd"));
		ArgumentCaptor<Person> formObjectArgument = ArgumentCaptor.forClass(Person.class);
		verify(personRepository, times(1)).add(formObjectArgument.capture());
		verifyNoMoreInteractions(personRepository);

		Person person = formObjectArgument.getValue();

		assertThat(person.getVorname(), is("Tom"));
		assertNull(person.getId());
	}
	//TODO
	@Test
	public void editTEST() throws Exception {

		mockMvc.perform(get("/edit/{id}",1L))
				.andExpect(status().isOk())
				.andExpect(view().name("edit"))
				.andExpect(model().attribute("person",hasItem(
						allOf(
								hasProperty("vorname",is("Tom")),
								hasProperty("nachname",is("Stark")),
								hasProperty("jahreslohn",is("10000")),
								hasProperty("kontakt",is("tung@gmail.com")),
								//hasProperty("projekteId",is(new Long[]{Long.valueOf(1),Long.valueOf(2)})),
								hasProperty("skills",is(new String[]{"Java","Python"}))
						)
				)))
		;

		verify(personRepository, times(1)).findById(1L);
		verifyZeroInteractions(personRepository);
	}
	@Test
	public void mainpageTEST() throws Exception{
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("edit"))
				.andExpect(model().attribute("person",hasItem(
						allOf(
								hasProperty("vorname",is("Tom")),
								hasProperty("nachname",is("Stark")),
								hasProperty("jahreslohn",is("10000")),
								hasProperty("kontakt",is("tung@gmail.com")),
								//hasProperty("projekteId",is(new Long[]{Long.valueOf(1),Long.valueOf(2)})),
								hasProperty("skills",is(new String[]{"Java","Python"}))
						)
				)))
		;
	}
	@Test
	public void saveChangesTEST() throws Exception{

	}
}

