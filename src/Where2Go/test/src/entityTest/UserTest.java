package entityTest;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;
import persistence.ParseUtil;

/**
 * Testes da entidade usuario
 * Created by brunomb on 02/12/14.
 */
public class UserTest extends TestCase{
	private User user1, user2, user3, user4;
	private Event testEvent1, testEvent2, testEvent3, testEvent4, testEvent5;
	private Invitation invitation1, invitation2; 
	
	@Before
	public void setUp() throws ParseException {
		user1 = new User("User1 name");
		user2 = new User("User2 name");
		testEvent1 = new Event("Event 1", "Event 1 Description", "Event Image Path", ParseUtil.PT_BR.parse("01/12/2014"), ParseUtil.PT_BR.parse("02/12/2014"), 200.00, "", 100, true, new User("Anderson"));
		testEvent2 = new Event("Event 2", "Event 2 Description", "Event Image Path", ParseUtil.PT_BR.parse("01/12/2014"), ParseUtil.PT_BR.parse("02/12/2014"), 200.00, "", 100, true, new User("Bruno"));
		testEvent3 = new Event("Event 3", "Event 3 Description", "Event Image Path", ParseUtil.PT_BR.parse("01/12/2014"), ParseUtil.PT_BR.parse("02/12/2014"), 200.00, "", 100, true, new User("Diego"));
		testEvent4 = new Event("Event 4", "Event 4 Description", "Event Image Path", ParseUtil.PT_BR.parse("01/12/2014"), ParseUtil.PT_BR.parse("02/12/2014"), 200.00, "", 100, true, new User("Marcos"));
		testEvent5 = new Event("Event 5", "Event 5 Description", "Event Image Path", ParseUtil.PT_BR.parse("01/12/2014"), ParseUtil.PT_BR.parse("02/12/2014"), 200.00, "", 100, true, new User("Julio"));
		invitation1 = new Invitation(user1, user2, testEvent1);
	}
	
	@Test
	public void testUserConstructor() {
		user1 = null;
		user2 = null;
		user1 = new User("New Test User1");
		user2 = new User("New Test User2");
		Assert.assertNotNull("User constructor not working", user1);
		Assert.assertNotNull("User constructor not working", user2);
	}
	
	@Test
	public void testGetName() {
		Assert.assertNotNull("User getName not working", user1.getName());
		Assert.assertNotNull("User getName not working", user2.getName());
		Assert.assertEquals("User getName not working", "User1 name", user1.getName());
		Assert.assertNotSame("User getName not working", "User1 name", user2.getName());
	}
	
	@Test
	public void testSetName() {
		user1.setName("New User1 name");
		Assert.assertEquals("User SetName not working", "New User1 name", user1.getName());
		Assert.assertNotSame("User setName not working", "User1 name", user1.getName());
		user2.setName("New User2 name");
		Assert.assertEquals("User SetName not working", "New User2 name", user2.getName());
		Assert.assertNotSame("User setName not working", "User2 name", user2.getName());
	}
	
	@Test
	public void testGetAge() {
		Assert.assertNotNull("User getAge not working", user1.getAge());
		Assert.assertNotNull("User getAge not working", user2.getAge());
		Assert.assertEquals("User getAge not working", 25, user1.getAge());
		Assert.assertNotSame("User getAge not working", 25, user2.getAge());
	}
	
	@Test
	public void testSetAge() {
		user1.setAge(50);
		Assert.assertEquals("User SetAge not working", 50, user1.getAge());
		Assert.assertNotSame("User setAge not working", 25, user1.getAge());
		user2.setAge(45);
		Assert.assertEquals("User SetAge not working", 45, user2.getAge());
		Assert.assertNotSame("User SetAge not working", 30, user2.getAge());
	}
	
	@Test
	public void testHashCode() {
		Assert.assertNotNull(user1.hashCode());
		Assert.assertNotNull(user2.hashCode());
	}
	
	@Test
	public void testEquals() {
		Assert.assertFalse("User Equals not working", user1.equals(user2));
		user2.setName("User1 name");
		user2.setAge(25);
		Assert.assertTrue("User Equals not working", user1.equals(user2));
	}
	
}
