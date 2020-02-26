import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class CoffeeMakerQuestTest {

	CoffeeMakerQuest cmq;
	@Mock
	Player player;
	@Mock
	Room room1;	// Small room
	@Mock
	Room room2;	// Funny room
	@Mock
	Room room3;	// Refinanced room
	@Mock
	Room room4;	// Dumb room
	@Mock
	Room room5;	// Bloodthirsty room
	@Mock
	Room room6;	// Rough room

	@Before
	public void setup() {
		// 0. Turn on bug injection for Player and Room.
		Config.setBuggyPlayer(true);
		Config.setBuggyRoom(true);
		
		// 1. Create the Coffee Maker Quest object and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance();

		// TODO: 2. Create a mock Player and assign to player and call cmq.setPlayer(player). 
		// Player should not have any items (no coffee, no cream, no sugar)
		player = mock(Player.class);
		cmq.setPlayer(player);
		when(player.checkCoffee()).thenReturn(false);
		when(player.checkCream()).thenReturn(false);
		when(player.checkSugar()).thenReturn(false);
		
		// TODO: 3. Create mock Rooms and assign to room1, room2, ..., room6.
		// Mimic the furnishings / adjectives / items of the rooms in the original Coffee Maker Quest.
		room1 = mock(Room.class);
		room2 = mock(Room.class);
		room3 = mock(Room.class);
		room4 = mock(Room.class);
		room5 = mock(Room.class);
		room6 = mock(Room.class);
		
		when(room1.getFurnishing()).thenReturn("Quaint sofa");
		when(room2.getFurnishing()).thenReturn("Sad record player");
		when(room3.getFurnishing()).thenReturn("Tight pizza");
		when(room4.getFurnishing()).thenReturn("Flat energy drink");
		when(room5.getFurnishing()).thenReturn("Beautiful bag of money");
		when(room6.getFurnishing()).thenReturn("Perfect air hockey table");
		
		when(room1.getAdjective()).thenReturn("Small");
		when(room2.getAdjective()).thenReturn("Funny");
		when(room3.getAdjective()).thenReturn("Refinanced");
		when(room4.getAdjective()).thenReturn("Dumb");
		when(room5.getAdjective()).thenReturn("Bloodthirsty");
		when(room6.getAdjective()).thenReturn("Rough");
		
		when(room1.getItem()).thenReturn(Item.CREAM);
		when(room2.getItem()).thenReturn(Item.NONE);
		when(room3.getItem()).thenReturn(Item.COFFEE);
		when(room4.getItem()).thenReturn(Item.NONE);
		when(room5.getItem()).thenReturn(Item.NONE);
		when(room6.getItem()).thenReturn(Item.SUGAR);
		// TODO: 4. Add the rooms created above to mimic the layout of the original Coffee Maker Quest.
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Magenta", "Massive");
		cmq.addRoomAtNorth(room3, "Beige", "Smart");
		cmq.addRoomAtNorth(room4, "Dead", "Slim");
		cmq.addRoomAtNorth(room5, "Vivacious", "Sandy");
		cmq.addRoomAtNorth(room6, "Purple", "Minimalist");
	}

	@After
	public void tearDown() {
	}
	
	/**
	 * Test case for String getInstructionsString().
	 * Preconditions: None.
	 * Execution steps: Call cmq.getInstructionsString().
	 * Postconditions: Return value is " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 */
	@Test
	public void testGetInstructionsString() {
		assertEquals(cmq.getInstructionsString(), " INSTRUCTIONS (N,S,L,I,D,H) > ");
	}
	
	/**
	 * Test case for boolean addFirstRoom(Room room).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.addFirstRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testAddFirstRoom() {
		Room myRoom = mock(Room.class);
		assertFalse(cmq.addFirstRoom(myRoom));
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Fake bed" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is true.
	 *                 room6.setNorthDoor("North") is called.
	 *                 myRoom.setSouthDoor("South") is called.
	 */
	@Test
	public void testAddRoomAtNorthUnique() {
		Room myRoom = mock(Room.class);
		assertTrue(cmq.addRoomAtNorth(myRoom, "North", "South"));
		verify(room6, times(1)).setNorthDoor("North");
		verify(myRoom, times(1)).setSouthDoor("South");
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Flat energy drink" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddRoomAtNorthDuplicate() {
		Room myRoom = mock(Room.class);
		when(myRoom.getFurnishing()).thenReturn("Flat energy drink");
		assertFalse(cmq.addRoomAtNorth(myRoom, "North", "South"));
		verify(room6, times(0)).setNorthDoor("North");
		verify(myRoom, times(0)).setSouthDoor("South");
	}
	
	/**
	 * Test case for Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room) has not yet been called.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is null.
	 */
	@Test
	public void testGetCurrentRoom() {
		assertNull(cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room room) has not yet been called.
	 * Execution steps: Call cmq.setCurrentRoom(room3).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(room3) is true. 
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testSetCurrentRoom() {
		assertTrue(cmq.setCurrentRoom(room3));
		assertEquals(cmq.getCurrentRoom(), room3);
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: None.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandI() {
		// TODO
		assertEquals("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n", cmq.processCommand("I"));
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some creamy cream!\n".
	 *                 player.addItem(Item.CREAM) is called.
	 */
	@Test
	public void testProcessCommandLCream() {
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("l"), "There might be something here...\nYou found some creamy cream!\n");
		verify(player, times(1)).addItem(Item.CREAM);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandN() {
		cmq.setCurrentRoom(room4);
		assertEquals(cmq.processCommand("n"), "");
		assertEquals(cmq.getCurrentRoom(), room5);
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandS() {
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("s"), "A door in that direction does not exist.\n");
		assertEquals(cmq.getCurrentRoom(), room1);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLose() {
		assertEquals(cmq.processCommand("D"), "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n");
		assertTrue(cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWin() {
		when(player.checkCoffee()).thenReturn(true);
		when(player.checkCream()).thenReturn(true);
		when(player.checkSugar()).thenReturn(true);
		assertEquals(cmq.processCommand("D"), "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n");
		assertTrue(cmq.isGameOver());
	}
	
	// TODO: Put in more unit tests of your own making to improve coverage!
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: cmq.setCurrentRoom(room6) has been run
	 * Execution steps: call cmq.processCommand("n").
	 * 					call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "Can't go norther bro!\n".
	 * 				   Return value of cmq.getCurrentRoom is room6.
	 */
	@Test
	public void testProcessCommandNWhenNorthest() {
		cmq.setCurrentRoom(room6);
		assertEquals(cmq.processCommand("n"), "Can't go norther bro!\n");
		assertEquals(cmq.getCurrentRoom(), room6);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has the item COFFEE
	 * Execution Steps: call cmq.processCommand("D").
	 * 					call cmq.isGameOver();
	 * Postconditions: Return value will be "You have a cup of delicious coffee.\n\nIf only your soul were as dark as this coffee, you might actually enjoy it.\n You Lose!".
	 * 				   cmq.isGameOver() will return true;
	 */	
	@Test
	public void testProcessCommandDWithOnlyCoffee() {
		when(player.checkCoffee()).thenReturn(true);
		assertEquals(cmq.processCommand("D"), "You have a cup of delicious coffee.\n\nIf only your soul were as dark as this coffee, you might actually enjoy it.\n You Lose!");
		assertTrue(cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: Player will have all 3 items, COFFEE, CREAM, and SUGAR
	 * Execution Steps: call cmq.processCommand("I").
	 * Postconditions: return value will be "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n".
	 */
	@Test
	public void testProcessCommandIWithAllItems() {
		when(player.checkCoffee()).thenReturn(true);
		when(player.checkCream()).thenReturn(true);
		when(player.checkSugar()).thenReturn(true);
		
		assertEquals(cmq.processCommand("I"), "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n");
	}
	/**
	 * Test case for addRoomAtNorth(Room room, String northDoor, String southDoor)
	 * Preconditions: Create a mock Room called "myRoom"
	 * Execution Steps: call cmq.addRoomAtNorth(myRoom, "Whateves", null).
	 * Postconditions: return value will be false
	 */
	@Test
	public void testAddRoomAtNorthWithANullArgument() {
		Room myRoom = mock(Room.class);
		assertFalse(cmq.addRoomAtNorth(myRoom, "Whateves", null));
	}
	
	/**
	 * Test case for processCommand("S").
	 * Preconditions: cmq.setCurrentRoom(room2) has been run.
	 * Execution Steps: call cmq.processCommand("S").
	 * Postconditions: return value will be "".
	 */
	@Test
	public void testProcessCommandGoingSouth() {
		cmq.setCurrentRoom(room2);
		assertEquals("", cmq.processCommand("S"));
	}
	/**
	 * Test case for String processCommand("H")
	 * Preconditions: None
	 * Execution Steps: call cmq.processCommand("H").
	 * Postconditions: returns " INSTRUCTIONS (N,S,L,I,D,H) "
	 */
	@Test
	public void testProcessCommandHelp() {
		assertEquals(cmq.processCommand("H"), " INSTRUCTIONS (N,S,L,I,D,H) > ");
	}
	/**
	 * Test case for String processCommand("Z")
	 * Preconditions: None
	 * Execution Steps: call cmq.processCommand("Z").
	 * Postconditions: returns "Please enter a valid command.\nFor a full list of commands enter \"H\""
	 */
	@Test
	public void testProcessCommandUnknown() {
		assertEquals(cmq.processCommand("Z"), "Please enter a valid command.\nFor a full list of commands enter \"H\"");
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has SUGAR and CREAM but not COFFEE
	 * Execution Steps: call cmq.processCommand("D")
	 * Postconditions: returns "You have some fresh cream.\nYou have some tasty sugar.\n\nIronically enough, the two best parts of the coffee experience don't work well without the actual coffee.\n You Lose!";
	 */
	@Test
	public void testProcessCommandSugarAndCream() {
		when(player.checkCream()).thenReturn(true);
		when(player.checkSugar()).thenReturn(true);
		
		assertEquals(cmq.processCommand("D"), "You have some fresh cream.\nYou have some tasty sugar.\n\nIronically enough, the two best parts of the coffee experience don't work well without the actual coffee.\n You Lose!");
	}
	
	/**
	 * Test case for String funDrink().
	 * Preconditions: Player has all 3 items
	 * Execution Steps: call cmq.processCommand("D", true).
	 * Postconditions: return "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\nWOW! For some inexplicable reason that drink of coffee was especially fun!!!".
	 */
	@Test
	public void testFun() {
		when(player.checkSugar()).thenReturn(true);
		
		assertEquals(cmq.processCommand("D"),"You have some tasty sugar.\n\nYou fixing for a sugar fix but you're not that desperate... yet.\n\nYou Lose!");
	}
}
	
	

