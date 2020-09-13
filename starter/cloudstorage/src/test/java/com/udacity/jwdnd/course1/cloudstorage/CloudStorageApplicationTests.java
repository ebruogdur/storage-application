package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private NotePage notePage;
	private CredentialPage credentialPage;
	private final String userName = "Admin";
	private final String password = "1";

	private Note note1;
	private Note note2;

	private Credential credential1;
	private Credential credential2;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}


	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {

		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
		notePage = new NotePage(driver);
		credentialPage = new CredentialPage(driver);
		note1 = new Note();
		note1.setNoteTitle("title");
		note1.setNoteDescription("desc");
		note2 = new Note();
		note2.setNoteTitle("edit title");
		note2.setNoteDescription("edit desc");
		credential1 = new Credential();
		credential1.setUrl("https://www.google.com");
		credential1.setUsername("Admin");
		credential1.setPassword("1");
		credential2 = new Credential();
		credential2.setUrl("https://github.com");
		credential2.setUsername("Admin2");
		credential2.setPassword("2");
	}

	/*
	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
*/
	@Test
	@Order(1)
	public void testHomePageNotAccessible() throws InterruptedException {
		driver.navigate().to("http://localhost:" + port + "/home");
		assertEquals(loginPage.getPageHeader(), "Login");
	}

	@Test
	@Order(2)
	public void testNavigateToSignUpPageButton() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.clickOnSignUpPage();
		assertEquals(signupPage.getPageHeader(), "Sign Up");
	}

	@Test
	@Order(3)
	public void testSignUp() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		String firstName = "Ebru";
		String lastName = "Ogdur";
		signupPage.signup(firstName, lastName, userName, password);
		String successMessage = signupPage.getSuccessMessage();
		assertTrue(successMessage.contains("You successfully signed up!"));
	}

	@Test
	@Order(4)
	public void testLogin() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
	}

	@Test
	@Order(5)
	public void testHomePageNotAccessibleAfterLogout() throws InterruptedException {
		homePage.logout();
		driver.navigate().to("http://localhost:" + port + "/home");
		assertEquals(loginPage.getPageHeader(), "Login");
	}

	@Test
	@Order(6)
	public void testAddNote() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		notePage.openNoteTab();
		notePage.addNewNote(note1);
		Note added = notePage.getNote();
		assertEquals(added.getNoteTitle(), note1.getNoteTitle());
		assertEquals(added.getNoteDescription(), note1.getNoteDescription());
		homePage.logout();

	}

	@Test
	@Order(7)
	public void testEditNote() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		notePage.openNoteTab();
		notePage.editNote(note2);
		Note edited = notePage.getNote();
		assertEquals(edited.getNoteTitle(), note2.getNoteTitle());
		assertEquals(edited.getNoteDescription(), note2.getNoteDescription());
		homePage.logout();

	}

	@Test
	@Order(8)
	public void testDeleteNote() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		notePage.openNoteTab();
		notePage.deleteNote();
		String noNoteText = notePage.getNoNoteText();
		assertTrue(noNoteText.contains("Hiç Note Yok!"));
		homePage.logout();

	}

	@Test
	@Order(9)
	public void testAddCredential() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		credentialPage.navigateToCredentialTab();
		credentialPage.addNewCredential(credential1);

		Credential added = credentialPage.getCredential();
		assertEquals(added.getUrl(), credential1.getUrl());
		System.out.println(credential1.getPassword()+"/"+added.getPassword());
		assertEquals(added.getUsername(), credential1.getUsername());
		assertEquals(added.getPassword(), credential1.getPassword());
		homePage.logout();
		Thread.sleep(1000);
	}

	@Test
	@Order(10)
	public void testEditCredential() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		credentialPage.navigateToCredentialTab();
		credentialPage.editCredential(credential2);

		Credential edited = credentialPage.getCredential();
		assertEquals(edited.getUrl(), credential2.getUrl());
		System.out.println(credential2.getPassword()+"/"+edited.getPassword());
		assertEquals(edited.getUsername(), credential2.getUsername());
		assertEquals(edited.getPassword(), credential2.getPassword());
		homePage.logout();
		Thread.sleep(1000);
	}
	@Test
	@Order(11)
	public void testDeleteCredential() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		credentialPage.navigateToCredentialTab();
		credentialPage.deleteCredential();

		String noCredentialText = credentialPage.getNoCredentialText();
		assertTrue(noCredentialText.contains("Hiç Credential Yok!"));
		homePage.logout();
		Thread.sleep(1000);
	}



	@Test
	@Order(12)
	public void noteExistsInTable() throws InterruptedException {
		driver.get("http://localhost:" + port + "/login");
		loginPage.login(userName, password);
		notePage.openNoteTab();
		notePage.addNewNote(note1);
		Note added = notePage.getNote();
		boolean b = notePage.noteExistsInTable(added.getNoteTitle(),added.getNoteDescription());
		assertEquals(true, b);
		homePage.logout();

	}


}
