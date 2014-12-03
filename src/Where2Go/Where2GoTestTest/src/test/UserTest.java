package test;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

/**
 * Testes da entidade usuario
 * Created by brunomb on 02/12/14.
 */
public class UserTest extends TestCase{
	private User user1, user2;
	private Event testEvent1;
	private Invitation invitation1, invitation2; 
	
	public void setUp() {
		user1 = new User("User1 name", 25);
		user2 = new User("User2 name", 30);
		testEvent1 = new Event(1, "Event 1", "Opened", "Event 1 Description", "Event Image Path", "Event 1 Info", "01/12/2014", "02/12/2014", "200,00", "", 100, "01/12/2014");
		invitation1 = new Invitation(user1, testEvent1);
	}
	
	@Test
	public void testUserConstrucot() {
		user1 = null;
		user2 = null;
		user1 = new User("New Test User1",20);
		user2 = new User("New Test User2",30);
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
	public void testAddInvitation() {
		List<Invitation> user1Invitatios = user1.getInvitations();
		List<Invitation> user2Invitatios = user2.getInvitations();
		Assert.assertNotNull("User addInvitation not working", user1Invitatios);
		Assert.assertNotNull("User addInvitation not working", user2Invitatios);
		Assert.assertEquals("User addInvitation not working",user1.getInvitations().size(),0);
		Assert.assertEquals("User addInvitation not working",user2.getInvitations().size(),0);
		user1.addInvitation(invitation1);
		Assert.assertEquals("User addInvitation not working",user1.getInvitations().size(),1);
		user2.addInvitation(invitation2);
		Assert.assertEquals("User addInvitation not working",user2.getInvitations().size(),1);
	}
	
	@Test
	public void testRemoveInvitations() {
		user1.addInvitation(invitation1);
		user2.addInvitation(invitation2);
		Assert.assertEquals("User removeIvitation not working",user1.getInvitations().size(),1);
		Assert.assertEquals("User removeIvitation not working",user2.getInvitations().size(),1);
		user1.removeInvitation(invitation1);
		Assert.assertEquals("User removeIvitation not working",user1.getInvitations().size(),0);
		user2.removeInvitation(invitation2);
		Assert.assertEquals("User removeIvitation not working",user2.getInvitations().size(),0);
	}
	
	@Test
	public void testGetInvitations() {
		user1.addInvitation(invitation1);
		user2.addInvitation(invitation2);
		Assert.assertEquals("User removeIvitation not working",user1.getInvitations().size(),1);
		Assert.assertEquals("User removeIvitation not working",user2.getInvitations().size(),1);
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
